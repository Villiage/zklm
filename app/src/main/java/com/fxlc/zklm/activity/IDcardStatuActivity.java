package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.UInfo;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.service.ContactService;
import com.fxlc.zklm.net.service.UserService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class IDcardStatuActivity extends BaseActivity {

    TextView nameTx,idnoTx,issueTx;
    UInfo uInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_statu);
        uInfo = (UInfo) getIntent().getSerializableExtra("uinfo");
         nameTx = (TextView) findViewById(R.id.name);
         idnoTx = (TextView) findViewById(R.id.id_no);
         issueTx = (TextView) findViewById(R.id.issue);
       nameTx.setText(uInfo.getIdcard().getName());
       idnoTx.setText(uInfo.getIdcard().getCardnumber());
       issueTx.setText(uInfo.getIdcard().getCardorgan());

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("个人信息");
    }
}
