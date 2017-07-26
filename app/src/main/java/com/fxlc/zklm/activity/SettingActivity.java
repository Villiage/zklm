package com.fxlc.zklm.activity;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.util.DataCleanManager;

import org.w3c.dom.Text;

import java.io.File;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    TextView cacheTxt;
    File cacheFile;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.feedback).setOnClickListener(this);
        findViewById(R.id.action).setOnClickListener(this);
        findViewById(R.id.clear_cache).setOnClickListener(this);
        findViewById(R.id.protocal).setOnClickListener(this);
        findViewById(R.id.aboutus).setOnClickListener(this);
        cacheTxt = (TextView) findViewById(R.id.cache);
        cacheFile = new File(Environment.getExternalStorageDirectory(),"com.zklm");
         getCache();

    }


    @Override
    protected void onResume() {
        super.onResume();
        title("设置");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.clear_cache:
                proDialog.setMessage("请稍候...");
                proDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DataCleanManager.cleanApplicationData(ctx,cacheFile.getPath());
                        getCache();
                        proDialog.dismiss();
                    }
                },1000 * 2);

                break;
            case R.id.feedback:
                it.setClass(ctx, FeedbackActivity.class);
                startActivity(it);

                break;
            case R.id.aboutus:
                it.setClass(ctx, AboutusActivity.class);
                startActivity(it);

                break;
            case R.id.protocal:
                it.setClass(ctx, ProtocalActivity.class);
                startActivity(it);

                break;
            case R.id.action:

                sp.edit().remove("user").commit();
                MyApplication.getInstance().exit();
                it.setClass(ctx,LoginActivity.class);
                startActivity(it);
                break;



        }
    }

    private void getCache(){
        try {

            String size = DataCleanManager.getCacheSize(cacheFile);
            cacheTxt.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
