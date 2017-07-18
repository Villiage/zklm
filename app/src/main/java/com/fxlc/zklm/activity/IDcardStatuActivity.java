package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;

import retrofit2.Retrofit;

public class IDcardStatuActivity extends BaseActivity {

    TextView nameTx,idnoTx,issueTx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_statu);
         nameTx = (TextView) findViewById(R.id.name);
         nameTx = (TextView) findViewById(R.id.id_no);
         nameTx = (TextView) findViewById(R.id.issue);

    }

    private void getData(){

        Retrofit retrofit = MyApplication.getRetrofit();



    }
}
