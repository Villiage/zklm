package com.fxlc.zklm.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.IDcard;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.UserService;
import com.fxlc.zklm.test.IDcardTestActivity;
import com.fxlc.zklm.util.BitmapUtil;
import com.fxlc.zklm.util.DialogUtil;
import com.fxlc.zklm.util.FileUtil;
import com.fxlc.zklm.util.UriUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class LicenseAuditActivity extends BaseActivity implements View.OnClickListener {

    public static int ALBUM_CODE = 100;
    public static int CAPTURE_CODE = 101;
    public static String captureTempFile = "license_temp.jpg";
    public File captureFile;
    private String licensePath;
    private boolean success, read = false;

    private TextView comNameTx;
    private String comName;
    private ImageView licenseImg;
    private byte[] content;

    private View verify;
    private ProgressDialog dialog;

    private Context context;




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1){

                toast("识别失败，请重新选取");
            }
            if (msg.what == 0) {
                String dataValue = (String) msg.obj;


                JSONObject outputObj = null;
                try {
                    outputObj = new JSONObject(dataValue);
                    if (!TextUtils.isEmpty(outputObj.getString("reg_num"))) {
                        success = true;

                    } else {
                        success = false;
                        toast("照片识别失败，请重新上传");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_license);

        comNameTx = (TextView) findViewById(R.id.com_name);
        licenseImg = (ImageView) findViewById(R.id.licenseImg);

        findViewById(R.id.license).setOnClickListener(this);

        verify = findViewById(R.id.verify);
        verify.setOnClickListener(this);


        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        choiceDialog = DialogUtil.createDialog(context, new String[]{"相册", "拍照"}, this);
    }


    OkHttpClient client = new OkHttpClient();

    Dialog choiceDialog = null;

    @Override
    protected void onResume() {
        super.onResume();
        title("公司认证");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.license:
//                    //权限已经被授予，在这里直接写要执行的相应方法即可
                choiceDialog.show();

                break;
            case R.id.verify:

                if (read) {
                    if (success)
                        submit();
                    else
                        Toast.makeText(context, "照片信息不完整或不清晰，请重新选取", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(licensePath))
                    submit();

                break;
            case R.id.dialog_item1:
                choiceDialog.dismiss();
                Intent album = new Intent(Intent.ACTION_PICK);
                album.setType("image/*");
                startActivityForResult(album, ALBUM_CODE);
                break;
            case R.id.dialog_item2:

                choiceDialog.dismiss();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);


                break;
            case R.id.dialog_close:
                choiceDialog.dismiss();
                break;

        }
    }
    private void  photo(){
        captureTempFile += System.currentTimeMillis();
        Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        captureFile = FileUtil.getSaveFile(context, captureTempFile);
        Uri uri = Uri.fromFile(captureFile);
        takephoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(takephoto, CAPTURE_CODE);
    }

    public void readCard(String photoPath) {
        dialog.setMessage("正在验证照片,请稍侯...");
        dialog.show();

        final String host = "http://dm-58.data.aliyun.com";
        final String path = "/rest/160601/ocr/ocr_business_license.json";
        final String appcode = "60091d0fb1a648459827272c0abf7909";

        content = BitmapUtil.cpPicToByte(photoPath, 200);
        String imgBase64 = android.util.Base64.encodeToString(content, android.util.Base64.DEFAULT);

        final JSONObject requestObj = new JSONObject();
        try {

            JSONObject imgobj = new JSONObject();
            JSONArray inputArray = new JSONArray();
            imgobj.put("dataType", 50);
            imgobj.put("dataValue", imgBase64);
            JSONObject obj = new JSONObject();
            obj.put("image", imgobj);
            inputArray.put(obj);
            requestObj.put("inputs", inputArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String body = requestObj.toString();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, body);
        final Request request = new Request.Builder()
                .url(host + path)
                .header("Authorization", "APPCODE " + appcode)
                .header("Content-Type", "application/json; charset=UTF-8")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();

                toast("识别失败，请重新选取");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                Toast.makeText(context, response.code() + "", Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    try {
                        String result = response.body().string();
                        Log.d("response1", result);
                        JSONObject obj = new JSONObject(result);
                        JSONArray outputs = obj.getJSONArray("outputs");
                        String outputValue = outputs.getJSONObject(0).optJSONObject("outputValue").getString("dataValue");

                        Message msg = Message.obtain();
                        msg.what = 0;
                        msg.obj = outputValue;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(-1);
                    }

                } else {

                    handler.sendEmptyMessage(-1);
                }
            }
        });


    }

    public void submit() {
        comName = comNameTx.getText().toString();
        proDialog.show();
        user = MyApplication.getUser();
        Retrofit retrofit = MyApplication.getRetrofit();
        UserService service = retrofit.create(UserService.class);

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/otcet-stream"), BitmapUtil.cpPicToByte(licensePath,100));

        retrofit2.Call<HttpResult> call = service.saveCompany(comName, requestBody);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {
                proDialog.dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<HttpResult> call, Throwable throwable) {
                super.onFailure(call, throwable);
                proDialog.dismiss();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                photo();
            } else {
                // Permission Denied
                Toast.makeText(this, "请在设置->权限中打开拍照权限", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            String photoPath = null;
            if (requestCode == ALBUM_CODE) {
                Uri uri = data.getData();
                photoPath = UriUtil.getRealFilePath(this, uri);

            }
            if (requestCode == CAPTURE_CODE) {
                photoPath = captureFile.getPath();
            }
            Glide.with(context).load(licensePath = photoPath).override(100, 150).into(licenseImg);
            if (read) readCard(photoPath);
        }

    }


}
