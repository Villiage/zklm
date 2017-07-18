package com.fxlc.zklm.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Wallet;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.PayService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MyWalletActivity extends BaseActivity implements View.OnClickListener{
    Context context;
    TextView validAmountTx;
    Wallet wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_my_wallet);
        findViewById(R.id.mbrrow).setOnClickListener(this);
        findViewById(R.id.mpay).setOnClickListener(this);

        validAmountTx = (TextView) findViewById(R.id.validAmount);
        getMoney();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("我的钱包");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.mbrrow:

                it.setClass(context,MyBorrowActivity.class);
                it.putExtra("wallet",wallet);
                startActivity(it);
                break;
            case R.id.mpay:
                it.setClass(context,PayHistoryActivity.class);
                startActivity(it);
                break;
        }
    }

    private void getMoney(){
        Retrofit retrofit = MyApplication.getRetrofit();

        PayService service = retrofit.create(PayService.class);

        Call<HttpResult<Wallet>> call = service.userMoney();
        call.enqueue(new HttpCallback<Wallet>() {
            @Override
            public void onSuccess(Wallet wallet) {
                MyWalletActivity.this.wallet = wallet;
                validAmountTx.setText(wallet.getSummoney());

            }
        });
    }
}
