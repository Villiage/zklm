package com.fxlc.zklm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.IDcard;
import com.fxlc.zklm.bean.User;

public class MyActivity extends BaseActivity implements View.OnClickListener {


    User user;
    TextView mobileTxt, nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = MyApplication.getUser();
        setContentView(R.layout.activity_my);
        findViewById(R.id.wallet).setOnClickListener(this);
        findViewById(R.id.uinfo).setOnClickListener(this);
        findViewById(R.id.contact).setOnClickListener(this);
        findViewById(R.id.car).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);

        mobileTxt = (TextView) findViewById(R.id.mobile);
        nameTxt = (TextView) findViewById(R.id.name);
        if (user != null) {
            mobileTxt.setText(user.getMobile());
            if (user.getName() != null) {
                nameTxt.setText(user.getName());
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("我的");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallet:
                it.setClass(ctx, MyWalletActivity.class);
                startActivity(it);
                break;
            case R.id.uinfo:

                it.setClass(ctx, UInfoActivity.class);
                startActivity(it);

                break;
            case R.id.contact:
                it.setClass(ctx, MyContactActivity.class);
                startActivity(it);
                break;
            case R.id.car:
                it.setClass(ctx, MycarActivity.class);
                startActivity(it);
                break;
            case R.id.setting:
                it.setClass(ctx, SettingActivity.class);
                startActivity(it);
                break;

        }
    }
}
