package com.fxlc.zklm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Wallet;

public class MyBorrowActivity extends BaseActivity implements View.OnClickListener {
    Wallet wallet;
    TextView borrowAmountTx,repayAmountTx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        setContentView(R.layout.activity_my_borrow);
        findViewById(R.id.detail).setOnClickListener(this);
        findViewById(R.id.action).setOnClickListener(this);
        if (wallet != null){
            borrowAmountTx = (TextView) findViewById(R.id.borrow_amount);
            repayAmountTx = (TextView) findViewById(R.id.repay_amount);
            borrowAmountTx.setText(wallet.getLoanmoney());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("我的借款");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail:
                it.setClass(ctx, BorrowHistoryActivity.class);
                startActivity(it);
                break;
         case R.id.action:
                it.setClass(ctx, RepayAcountActivity.class);
                startActivity(it);
                break;


        }
    }
}
