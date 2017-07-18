package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.feedback).setOnClickListener(this);
        findViewById(R.id.action).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("设置");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback:
                it.setClass(ctx, FeedbackActivity.class);
                startActivity(it);

                break;
            case R.id.action:

                sp.edit().remove("user").commit();
                MyApplication.getInstance().exit();
                it.setClass(ctx,LoginActivity.class);
                startActivity(it);
                break;
        }
    }
}
