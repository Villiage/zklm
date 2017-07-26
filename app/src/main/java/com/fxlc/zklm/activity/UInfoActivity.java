package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.UInfo;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UInfoActivity extends BaseActivity implements View.OnClickListener {


    private UInfo uInfo;
    private User user;
    private TextView mobileTxt, psStatuTxt, comStatuTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uinfo);
        mobileTxt = (TextView) findViewById(R.id.mobile);
        psStatuTxt = (TextView) findViewById(R.id.ps_statu);
        comStatuTxt = (TextView) findViewById(R.id.company_statu);

        findViewById(R.id.company).setOnClickListener(this);
        findViewById(R.id.idcard).setOnClickListener(this);
        findViewById(R.id.setpwd).setOnClickListener(this);
        findViewById(R.id.paypwd).setOnClickListener(this);
        user = MyApplication.getUser();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
        title("我的资料");
    }

    private void setValue(UInfo uInfo) {
        UInfo.UserBean userBean = uInfo.getUser();
        mobileTxt.setText(userBean.getMobile());
        if (userBean.getPstatus() == 0) {
            psStatuTxt.setText("未认证");
        } else  if(userBean.getPstatus() == 2){
            psStatuTxt.setText("已认证");
        }
        if (userBean.getComstatus() == 0) {
            comStatuTxt.setText("未认证");
        } else if (userBean.getComstatus() == 1) {
            comStatuTxt.setText("审核中");
        } else if (userBean.getComstatus() == 2) {
            comStatuTxt.setText("已认证");
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idcard:
                if (uInfo != null) {
                    if (uInfo.getUser().getPstatus() == 0) {
                        it.setClass(ctx, IDcardAuditActivity.class);
                    } else {
                        it.setClass(ctx, IDcardStatuActivity.class);
                        it.putExtra("uinfo",uInfo);
                    }
                    startActivity(it);
                }
                break;
            case R.id.company:
                if (uInfo != null) {
                    if (uInfo.getUser().getComstatus() == 0)
                        it.setClass(ctx, LicenseAuditActivity.class);
                    else {
                        it.setClass(ctx, LicenceStatuActivity.class);
                        it.putExtra("com",uInfo.getCompany());
                    }
                    startActivity(it);
                }
                break;
            case R.id.setpwd:

                it.setClass(ctx, ReSetPwdActivity.class);
                startActivity(it);
                break;
            case R.id.paypwd:

                it.setClass(ctx, ChangePayPwdActivity.class);
                it.putExtra("title","修改支付密码");
                startActivity(it);
                break;

        }
    }

    public void getInfo() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();
        UserService service = retrofit.create(UserService.class);
        Map<String, String> param = new HashMap<>();
        param.put("id", user.getId());
        Call<HttpResult<UInfo>> call = service.getUInfo(param);

        call.enqueue(new HttpCallback<UInfo>() {


            @Override
            public void onSuccess(UInfo info) {
                proDialog.dismiss();
                if (info != null){
                    setValue(uInfo = info);

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
                proDialog.dismiss();
            }
        });


    }
}
