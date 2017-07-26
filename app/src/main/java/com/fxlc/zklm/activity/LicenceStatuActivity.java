package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.UInfo;

public class LicenceStatuActivity extends BaseActivity {
    TextView comNameTxt;
    ImageView licenseImg;
    private UInfo.Company company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence_statu);

        comNameTxt = (TextView) findViewById(R.id.com_name);
        licenseImg = (ImageView) findViewById(R.id.licenseImg);

        company = (UInfo.Company) getIntent().getSerializableExtra("com");

        comNameTxt.setText(company.getCompanyName());
        Log.d("imgUrl", company.getBusinessLicense());
        Glide.with(this).load(company.getBusinessLicense()).override(150, 200).into(licenseImg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("公司信息");
    }
}
