package com.fxlc.zklm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {
   private TextView titleTxt;
    private  View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        back = findViewById(R.id.back);
        titleTxt = (TextView) findViewById(R.id.title);

    }

    public void title(String title) {
        titleTxt.setText(title);
    }
}
