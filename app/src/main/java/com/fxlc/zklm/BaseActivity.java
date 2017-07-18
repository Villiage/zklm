package com.fxlc.zklm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.util.DialogUtil;

public class BaseActivity extends AppCompatActivity {
   private TextView titleTxt;
    private  View back;
    public Context ctx;
    public  Intent it = new Intent();
    public static String TAG  = "HttpResp";
    public ProgressDialog proDialog;
    public SharedPreferences sp ;
    public  String netErrorMsg = "网络异常";
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        sp = MyApplication.getSP();
        user = MyApplication.getUser();
        proDialog = new ProgressDialog(this);
        proDialog.setMessage("加载中");

    }

    @Override
    protected void onResume() {
        super.onResume();
        back = findViewById(R.id.back);
        titleTxt = (TextView) findViewById(R.id.title);
        if (back != null)
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void title(String title) {
        titleTxt.setText(title);
    }

    protected void toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    protected void save(String  key ,String str){
        sp.edit().putString(key,str).commit();
    }
}
