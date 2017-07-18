package com.fxlc.zklm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.SettingService;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Retrofit;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    TextView contentTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        findViewById(R.id.action).setOnClickListener(this);
        contentTx = (TextView) findViewById(R.id.content);

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("意见反馈");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.action:


                feedBack();
                break;

        }
    }

    private void feedBack() {
        String feed = contentTx.getText().toString().trim();
        Retrofit retrofit = MyApplication.getRetrofit();

        SettingService service = retrofit.create(SettingService.class);

        Call<HttpResult> call = service.feedback(feed);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {
                    toast("感谢您的反馈!");
                    finish();
            }
        });


    }
}
