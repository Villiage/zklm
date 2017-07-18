package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.UserService;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SetPwdActivity extends BaseActivity  implements View.OnClickListener{
    private TextView mPhoneView,mVerifyCodeView,mPwdView,mRePwdView;
    private  String phone,pass,repass,verifyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("忘记密码");
    }

    private void  initView(){

        mPhoneView = (EditText) findViewById(R.id.phone);
        mVerifyCodeView = (TextView) findViewById(R.id.verifyCode);
        mPwdView = (EditText) findViewById(R.id.pwd);
        mRePwdView = (EditText) findViewById(R.id.repwd);
        findViewById(R.id.getverify).setOnClickListener(this);

        findViewById(R.id.action).setOnClickListener(this);
    }
    private  void getValue(){
        phone =  mPhoneView.getText().toString();
        pass = mPwdView.getText().toString();
        repass = mRePwdView.getText().toString();
        verifyCode = mVerifyCodeView.getText().toString();



    }


    private void change(){
        Retrofit retrofit =  MyApplication.getInstance().getRetrofit();
        UserService apiService = retrofit.create(UserService.class);

        HashMap map = new HashMap();
        map.put("mobile",phone);
        map.put("yzm",verifyCode);
        map.put("newpass",pass);


        MediaType.parse("");

        Call<String> call = apiService.setpwd(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        Log.d("httpresponse:",response.toString());

                    }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }






    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case  R.id.action:
                getValue();
                change();

                break;
        }
    }


}
