package com.fxlc.zklm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.test.PullActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Intent it = new Intent();
            it.setClass(this, HomeActivity.class);
            startActivity(it);
            finish();


    }
}
