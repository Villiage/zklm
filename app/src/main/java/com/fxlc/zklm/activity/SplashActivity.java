package com.fxlc.zklm.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fxlc.zklm.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = new Intent();
        it.setClass(this, EntryActivity.class);
        startActivity(it);
        finish();
    }
}
