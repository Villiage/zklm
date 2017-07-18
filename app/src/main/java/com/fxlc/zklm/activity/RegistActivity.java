package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fxlc.zklm.R.id.reg;

public class RegistActivity extends BaseActivity implements View.OnClickListener{
    private TextView phoneTxt,passTxt,repassTxt,verifyCodeTxt,inviteCodeTxt;
    private View  actionBt;
    private String phone,pass,repass,verifyCode,inviteCode;
    UserService apiService;
    private  int errorCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();

    }
    private void initView(){

        phoneTxt = (TextView) findViewById(R.id.phone);
        verifyCodeTxt = (TextView) findViewById(R.id.verifyCode);
        passTxt = (TextView) findViewById(R.id.pwd);
        repassTxt = (TextView) findViewById(R.id.repwd);

        inviteCodeTxt = (TextView) findViewById(R.id.inviteCode);
        actionBt = findViewById(R.id.action);
        actionBt.setOnClickListener(this);
        findViewById(R.id.getsms).setOnClickListener(this);

    }
   private  void getValue(){

       phone = phoneTxt.getText().toString();
       verifyCode = verifyCodeTxt.getText().toString();
       pass = passTxt.getText().toString();
       repass = repassTxt.getText().toString();
       inviteCode = inviteCodeTxt.getText().toString();

   }
   public  void validate(){
        errorCode = 0;
       if(TextUtils.isEmpty(phone)) {
           Toast.makeText(ctx, phoneTxt.getHint(), Toast.LENGTH_SHORT).show();
           errorCode ++;
           return;
       }
       if(TextUtils.isEmpty(verifyCode)) {
           Toast.makeText(ctx, verifyCodeTxt.getHint(), Toast.LENGTH_SHORT).show();
           errorCode ++;
           return;
       }
       if(TextUtils.isEmpty(pass)) {
           Toast.makeText(ctx, passTxt.getHint(), Toast.LENGTH_SHORT).show();
           errorCode ++;
           return;
       }
       if(TextUtils.isEmpty(repass)) {
           Toast.makeText(ctx, repassTxt.getHint(), Toast.LENGTH_SHORT).show();
           errorCode ++;
           return;
       }
   }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action:

                getValue();
                validate();
                if (errorCode == 0)
                  reg();

                break;
            case R.id.getsms:

                getsms();
                break;
        }
    }

    private void reg(){


        apiService = MyApplication.getInstance().getRetrofit().create(UserService.class);
        HashMap map = new HashMap();
        map.put("mobile",phone);
        map.put("pass",pass);
        map.put("yzm",verifyCode);
        map.put("invitecode",inviteCode);

        Call<HttpResult> call = apiService.reg(map);
        call.enqueue(new Callback<HttpResult>() {
            @Override
            public void onResponse(Call<HttpResult> call, Response<HttpResult> response) {

                    HttpResult result = response.body();

                     toast(result.getMsg());

                    if (result.isSuccess()){
                        finish();
                    }

            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {

            }
        });

    }

    public void getsms(){
        apiService = MyApplication.getInstance().getRetrofit().create(UserService.class);
        String mobile  = phoneTxt.getText().toString();
        Call<HttpResult> call = apiService.getSms(mobile);
        call.enqueue(new Callback<HttpResult>() {

            @Override
            public void onResponse(Call<HttpResult> call, Response<HttpResult> response) {
                  Log.d(TAG,response.body().getMsg());
                  HttpResult result = response.body();
                  if (response.body().isSuccess()){
//                      verifyCode  = result.getMsg();
                      Toast.makeText(ctx,result.getMsg(),Toast.LENGTH_SHORT).show();
                  }
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {

            }
        });
    }
}
