package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePayPwdActivity extends BaseActivity implements View.OnClickListener {

    private TextView mPhoneView, mVerifyCodeView, mPwdView, mRePwdView;
    private String phone, pass, repass, verifyCode,title;
    private TextView getSmsTx;
     Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_paypwd);
        title = getIntent().getStringExtra("title");
        user = MyApplication.getUser();
        initView();
        retrofit = MyApplication.getInstance().getRetrofit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title(title);
    }

    private void initView() {

        mPhoneView = (TextView) findViewById(R.id.phone);
        mVerifyCodeView = (TextView) findViewById(R.id.verfiy_code);
        mPwdView = (EditText) findViewById(R.id.pwd);
        mRePwdView = (EditText) findViewById(R.id.repwd);


        mPhoneView.setText(user.getMobile());
        getSmsTx = (TextView) findViewById(R.id.getverify);
        findViewById(R.id.getverify).setOnClickListener(this);

        findViewById(R.id.action).setOnClickListener(this);

    }



    private void change() {
        proDialog.show();
        UserService apiService = retrofit.create(UserService.class);

        HashMap map = new HashMap();
        map.put("id",user.getId());
        map.put("mobile", phone);
        map.put("yzm", verifyCode);
        map.put("newpass", pass);



        Call<HttpResult> call = apiService.savePayPass(map);

        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {
                proDialog.dismiss();
                 toast("设置成功");
                  finish();
            }
        });

    }
    private void getValue() {
        phone = mPhoneView.getText().toString();
        pass = mPwdView.getText().toString();
        repass = mRePwdView.getText().toString();
        verifyCode = mVerifyCodeView.getText().toString();



    }

    private boolean valid(){

        if (TextUtils.isEmpty(verifyCode)){
            toast("请输入验证码");
           return false;
        }


        if (TextUtils.isEmpty(pass)){

            toast("请输入密码");
            return false;
        }
       if (TextUtils.isEmpty(repass)){
           toast("请再输一次密码");
           return  false;
       }

       if (!Pattern.matches(pwPattern,pass)){
           toast("密码必须是6位数字");
           return false;
       }
       if (!pass.equals(repass)){
           toast("两次输入密码不一致");
           return  false;
       }

       return  true;
    }
    String pwPattern = "^\\d{6}$";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getverify:
                getsms();
                break;
            case R.id.action:
                getValue();
                if (valid())
                change();

                break;
        }
    }

    int count = 60;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {

                count--;
                if (count > 0) {
                    getSmsTx.setText(count + "s");
                    getSmsTx.setClickable(false);

                    sendEmptyMessageDelayed(0, 1000);
                } else if (count == 0) {
                    getSmsTx.setText("获取验证码");
                    getSmsTx.setClickable(true);

                }


            }

        }
    };

    public void getsms() {
        count = 60;
        handler.sendEmptyMessage(0);
        UserService service = retrofit.create(UserService.class);
        String mobile = mPhoneView.getText().toString();
        Call<HttpResult> call = service.getSms(mobile);
        call.enqueue(new Callback<HttpResult>() {

            @Override
            public void onResponse(Call<HttpResult> call, Response<HttpResult> response) {
                Log.d(TAG, response.body().getMsg());
                HttpResult result = response.body();
                if (response.body().isSuccess()) {
//                      verifyCode  = result.getMsg();
//                      Toast.makeText(ctx, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {

            }
        });
    }


}
