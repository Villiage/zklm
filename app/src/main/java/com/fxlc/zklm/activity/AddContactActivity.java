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

import retrofit2.Call;
import retrofit2.Retrofit;

public class AddContactActivity extends BaseActivity implements View.OnClickListener {
    private Contact contact;
    String name ,phone;
    TextView nameTxt,phoneTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        nameTxt = (TextView) findViewById(R.id.name);
        phoneTxt = (TextView) findViewById(R.id.phone);
        contact = (Contact) getIntent().getSerializableExtra("contact");

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("添加联系人");
    }

    private void getValue(){

        name= nameTxt.getText().toString();
        phone = phoneTxt.getText().toString();

        if (!TextUtils.isEmpty(name))
            contact.setName(name);
        if (!TextUtils.isEmpty(phone))
            contact.setPhone(phone);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                getValue();
                del();
                break;
        }
    }

    private void del() {

        Retrofit retrofit = MyApplication.getRetrofit();
        ContactService service = retrofit.create(ContactService.class);
        Call<HttpResult> call = service.update(contact.getId(),contact.getName(),contact.getPhone());
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable throwable) {
                super.onFailure(call, throwable);
            }
        });
    }
}
