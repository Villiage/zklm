package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.ContactService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AddContactActivity extends BaseActivity implements View.OnClickListener {
    private Contact contact;
    String name, phone;
    TextView nameTxt, phoneTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        nameTxt = (TextView) findViewById(R.id.name);
        phoneTxt = (TextView) findViewById(R.id.phone);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        findViewById(R.id.next).setOnClickListener(this);
        if (contact != null) {
            nameTxt.setText(contact.getName());
            phoneTxt.setText(contact.getPhone());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("添加联系人");
    }

    private void getValue() {

        name = nameTxt.getText().toString();
        phone = phoneTxt.getText().toString();


    }

    private boolean notEmpty() {

        if (TextUtils.isEmpty(name)) {
            toast("名字不能为空");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            toast("手机号不能为空");
            return false;
        }

        return true;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                getValue();
                if (notEmpty()) {
                    if (contact == null) {

                        add(new Contact(name, phone));
                    } else {
                        modify();
                    }

                }

                break;
        }
    }

    private void modify() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();
        ContactService service = retrofit.create(ContactService.class);
        Call<HttpResult> call = service.update(contact.getId(), name, phone);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {
                proDialog.dismiss();
                toast("添加成功");
            }


        });
    }

    private void add(Contact contact) {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();
        ContactService service = retrofit.create(ContactService.class);
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        Call<HttpResult> call = service.addContact3(new Gson().toJson(contacts));
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {
                proDialog.dismiss();
                toast("添加成功");
            }


        });
    }
}
