package com.fxlc.zklm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.bean.Wallet;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.ContactService;
import com.fxlc.zklm.net.service.PayService;
import com.fxlc.zklm.util.DialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    List<Contact> contactList = new ArrayList<>();
    ListView listView;
    ContactAdapter adapter;
    Retrofit retrofit;
    View emptyView, toIdcard;
    TextView usableMoney, totalMoney;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final String[] arr = new String[]{"从手机联系人添加", "手动添加"};
        DialogUtil.createDialog(HomeActivity.this, arr, this);
        findViewById(R.id.add_contact).setOnClickListener(this);
        findViewById(R.id.upgrade).setOnClickListener(this);
        findViewById(R.id.my).setOnClickListener(this);
        findViewById(R.id.msg).setOnClickListener(this);

        toIdcard = findViewById(R.id.toIdcard);
        emptyView = findViewById(R.id.empty);
        toIdcard.setOnClickListener(this);

        usableMoney = (TextView) findViewById(R.id.usable_amount);
        totalMoney = (TextView) findViewById(R.id.total_amount);
        usableMoney.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!TextUtils.isEmpty(user.getName())) {
                    getContactStatu(contactList.get(i));
                } else {
                    toast("请先完成个人认证!");
                }

            }
        });
        if (user != null && TextUtils.isEmpty(user.getName())) {
            toIdcard.setVisibility(View.VISIBLE);
        } else {
            toIdcard.setVisibility(View.GONE);
        }
        findViewById(R.id.add_contact).setOnClickListener(this);
        retrofit = MyApplication.getRetrofit();
        dialog = new AlertDialog.Builder(this).create();

    }

    @Override
    protected void onResume() {
        super.onResume();
        user = MyApplication.getUser();
        if (user != null) {
            getMoney();
            getContact();
        } else {
            dialog.setMessage("您还未登陆");
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "登陆", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    it.setClass(ctx,LoginActivity.class);
                    startActivity(it);
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

//            LoginActivity.toThis();
        }
    }

    private void getContactStatu(final Contact contact) {

        ContactService service = retrofit.create(ContactService.class);
        Call<ResponseBody> call = service.idStatus(contact.getPhone());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    if (obj.getBoolean("success")) {
                        it.setClass(ctx, PayActivity.class);
                        it.putExtra("contact", contact);
                        it.putExtra("contact_name", obj.getJSONObject("body").getJSONObject("user").getString("name"));
                        startActivity(it);
                    } else {

                        toast(obj.getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ;

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toIdcard:
                it.setClass(ctx, IDcardAuditActivity.class);
                startActivity(it);

                break;

            case R.id.my:
                if (user != null) {
                    it.setClass(ctx, MyActivity.class);
                    startActivity(it);
                } else {
                    it.setClass(ctx, LoginActivity.class);
                    startActivity(it);
                }

                break;
            case R.id.msg:

                it.setClass(ctx, MsgActivity.class);
                startActivity(it);

                break;
            case R.id.upgrade:
                if (user != null) {
                    it.setClass(ctx, AddCarActivity.class);
                    startActivity(it);
                }

                break;
            case R.id.dialog_item1:
                Intent it = new Intent();
                it.setClass(HomeActivity.this, ContactActivity.class);
                startActivity(it);
                break;
            case R.id.dialog_item2:

                break;
            case R.id.add_contact:


                break;
        }
    }

    private void getMoney() {

        PayService service = retrofit.create(PayService.class);

        Call<HttpResult<Wallet>> call = service.userMoney();
        call.enqueue(new HttpCallback<Wallet>() {
            @Override
            public void onSuccess(Wallet wallet) {


                totalMoney.setText("总额度 " + wallet.getSummoney());
                usableMoney.setText(wallet.getUsablemoney());


            }
        });
    }

    private void getContact() {


        ContactService service = retrofit.create(ContactService.class);
        Call<ResponseBody> call = service.getContact();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String resultStr = response.body().string();
                    JSONObject json = new JSONObject(resultStr);
                    if (json.getBoolean("success")) {
                        String conJson = json.getJSONObject("body").getString("contact");
                        contactList = new Gson().fromJson(conJson, new TypeToken<List<Contact>>() {
                        }.getType());
                        if (contactList != null && contactList.size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            adapter = new ContactAdapter();
                            listView.setAdapter(adapter);
                        }
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    class ContactAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public Contact getItem(int i) {
            return contactList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView txt1, txt2;
            View check;
            Contact contact = contactList.get(i);
            if (view == null) {
//                int id = android.R.layout.simple_list_item_2;
                int id = R.layout.list_item_contact;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
            }
            txt1 = (TextView) view.findViewById(R.id.name);
            txt2 = (TextView) view.findViewById(R.id.mobile);
            check = view.findViewById(R.id.check);
            check.setVisibility(View.GONE);
            txt1.setText(contact.getName());
            txt2.setText(contact.getPhone());

            return view;
        }

    }

    long firstime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondtime = System.currentTimeMillis();
            if (secondtime - firstime > 2000) {
                Toast.makeText(ctx, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                firstime = System.currentTimeMillis();
                return true;
            } else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
