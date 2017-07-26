package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;

public class ProtocalActivity extends BaseActivity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocal);
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
//        web.addJavascriptInterface(new JavaScriptInterface(), "ncp");
        web.loadUrl("file:///android_asset/protocal.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("服务协议");
    }
}
