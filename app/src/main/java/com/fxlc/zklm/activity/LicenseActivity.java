package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fxlc.zklm.R;

public class LicenseActivity extends AppCompatActivity {
     ImageView licenseImg;
    View license;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        licenseImg = (ImageView) findViewById(R.id.licenseImg);
        license = findViewById(R.id.license);
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
