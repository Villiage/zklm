package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.UserService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePayPwdActivity extends BaseActivity implements View.OnClickListener {

    private TextView mPhoneView, mVerifyCodeView, mPwdView, mRePwdView;
    private TextView idNoTxt;
    private String phone, pass, repass, verifyCode, idNo;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_paypwd);
        user = MyApplication.getUser();
        initView();
        retrofit = MyApplication.getInstance().getRetrofit();
    }

    private void initView() {

        mPhoneView = (TextView) findViewById(R.id.phone);
        mVerifyCodeView = (TextView) findViewById(R.id.verfiy_code);
        mPwdView = (EditText) findViewById(R.id.pwd);
        mRePwdView = (EditText) findViewById(R.id.repwd);
        idNoTxt = (EditText) findViewById(R.id.id_no);

        mPhoneView.setText(user.getMobile());
        findViewById(R.id.getverify).setOnClickListener(this);

        findViewById(R.id.action).setOnClickListener(this);

    }

    private void getValue() {
        phone = mPhoneView.getText().toString();
        pass = mPwdView.getText().toString();
        repass = mRePwdView.getText().toString();
        verifyCode = mVerifyCodeView.getText().toString();
        idNo = idNoTxt.getText().toString();


    }


    private void change() {

        UserService apiService = retrofit.create(UserService.class);

        HashMap map = new HashMap();
        map.put("id",user.getId());
        map.put("mobile", phone);
        map.put("yzm", verifyCode);
        map.put("newpass", pass);
        map.put("cardnumber", idNo);


        Call<HttpResult> call = apiService.savePayPass(map);

        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getverify:
                getsms();
                break;
            case R.id.action:
                getValue();
                change();

                break;
        }
    }

    public void getsms() {
        UserService service = retrofit.create(UserService.class);
        String mobile = mPhoneView.getText().toString();
        Call<HttpResult> call = service.getSms(mobile);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

            }
        });
    }
}
