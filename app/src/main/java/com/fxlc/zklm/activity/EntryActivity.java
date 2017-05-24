package com.fxlc.zklm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fxlc.zklm.R;
import com.fxlc.zklm.test.DrawerActivity;
import com.fxlc.zklm.test.IDcardTestActivity;
import com.fxlc.zklm.test.ScanQRActivity;
import com.fxlc.zklm.test.ScrollingActivity;
import com.fxlc.zklm.test.TestActivity;

public class EntryActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_entry);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, IDcardTestActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, TestActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, ActivateActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, HomeActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, LoginActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, AddCarActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, ScanQRActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.collapsing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, ScrollingActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, DrawerActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.mycar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, MycarActivity.class);
                startActivity(it);

            }
        });
    }
}
