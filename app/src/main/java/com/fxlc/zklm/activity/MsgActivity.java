package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;

public class MsgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("消息");
    }
}
