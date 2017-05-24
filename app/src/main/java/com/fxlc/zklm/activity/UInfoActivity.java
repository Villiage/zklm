package com.fxlc.zklm.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fxlc.zklm.R;

public class UInfoActivity extends AppCompatActivity {
  private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.activity_uinfo);

    }
}
