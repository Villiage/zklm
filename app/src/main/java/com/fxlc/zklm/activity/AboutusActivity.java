package com.fxlc.zklm.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;

public class AboutusActivity extends BaseActivity implements View.OnClickListener {


    TextView versionTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        versionTx = (TextView) findViewById(R.id.version);

        versionTx.setText(getVersion());
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("关于我们");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "版本号 " + version;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return  "";
    }
}
