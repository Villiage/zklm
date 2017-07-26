package com.fxlc.zklm.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.ContactService;
import com.fxlc.zklm.util.DialogUtil;
import com.fxlc.zklm.util.DisplayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContactInfoActivity extends BaseActivity implements View.OnClickListener {
    Dialog dialog;
    Retrofit retrofit;
    Contact contact;
    TextView nameTx, phoneTx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        user = MyApplication.getUser();
        contact = (Contact) getIntent().getSerializableExtra("contact");
        findViewById(R.id.sendmsg).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.pay).setOnClickListener(this);
        nameTx = (TextView) findViewById(R.id.name);
        phoneTx = (TextView) findViewById(R.id.phone);


        FrameLayout topbar = (FrameLayout) findViewById(R.id.topbar);
        retrofit = MyApplication.getRetrofit();

        TextView addView = new TextView(this);
        addView.setTextColor(Color.WHITE);
        addView.setText("更多");
        addView.setId(R.id.add);
        addView.setOnClickListener(this);
        int pad = DisplayUtil.dp2px(this, 10);
        addView.setPadding(pad, pad, pad, pad);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        topbar.addView(addView, params);
        dialog = DialogUtil.createWBDialog(ctx, new String[]{ "修改联系人","删除联系人"}, this);

        nameTx.setText(contact.getName().substring(1,contact.getName().length()));
        phoneTx.setText(contact.getPhone());

    }

    @Override
    protected void onResume() {
        super.onResume();

        title("详情");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendmsg:

                Uri uri = Uri.parse("smsto:" + contact.getPhone());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                sendIntent.putExtra("sms_body", "");
                startActivity(sendIntent);
                break;
            case R.id.call:

                if (ContextCompat.checkSelfPermission(ctx,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                        Toast.makeText(ctx, "请授权！", Toast.LENGTH_LONG).show();
                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(intent);
                    }else{
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                301);
                    }
                }else {
                    // 已经获得授权，可以打电话
                     callPhone();
                }
                break;
            case R.id.pay:

                if (!TextUtils.isEmpty(user.getName())){
                    getContactStatu(contact);
                }else{
                    toast("请先完成个人认证!");
                }


                break;
            case R.id.add:
                dialog.show();

                break;

            case R.id.dialog_item1:
                dialog.dismiss();
                it.setClass(ctx, AddContactActivity.class);
                it.putExtra("contact",contact);
                startActivity(it);
                break;
            case R.id.dialog_item2:
                dialog.dismiss();
                del();
                break;

        }

    }
    private void callPhone(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.getPhone()));
        startActivity(intent);
    }
    private void del() {
        retrofit = MyApplication.getRetrofit();

        ContactService service = retrofit.create(ContactService.class);
        Call<HttpResult> call = service.del(contact.getId());
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

                    toast("删除成功");
                    finish();
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable throwable) {
                super.onFailure(call, throwable);
            }
        });
    }
    private void  getContactStatu(final Contact contact){

        ContactService service = retrofit.create(ContactService.class);
        Call<ResponseBody> call = service.idStatus(contact.getPhone());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    if (obj.getBoolean("success")){
                        it.setClass(ctx,PayActivity.class);
                        it.putExtra("contact",contact);
                        it.putExtra("contact_name",obj.getJSONObject("body").getJSONObject("user").getString("name"));
                        startActivity(it);
                    }else{
                        toast(obj.getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 301: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                      callPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }
}
