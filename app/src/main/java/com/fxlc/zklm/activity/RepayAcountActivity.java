package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.BankAccount;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.PayService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RepayAcountActivity extends BaseActivity {
   TextView repayAccountTx, accountNameTx,accountTx,bankTx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_acount);

        repayAccountTx = (TextView) findViewById(R.id.repay_account);
        accountNameTx = (TextView) findViewById(R.id.accountName);
        accountTx = (TextView) findViewById(R.id.account);
        bankTx = (TextView) findViewById(R.id.bank);

        request();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("还款账户");

    }

    private void  request(){
         Retrofit retrofit = MyApplication.getRetrofit();

         PayService service = retrofit.create(PayService.class);

         Call<HttpResult<BankAccount>> call = service.bank();
         call.enqueue(new HttpCallback<BankAccount>() {
             @Override
             public void onSuccess(BankAccount bankAccount) {

                   BankAccount.BanklistBean bank = bankAccount.getBanklist().get(0);
                   String account = bank.getBankNo();
                   StringBuffer sb = new StringBuffer();
                   for (int i = 0; i<= account.length()/4;i++){
                       if (i * 4 == account.length()) break;
                       int end = Math.min((i + 1) * 4,account.length());

                       sb.append(account.substring(i* 4,end));
                       sb.append(" ");
                   }


                   repayAccountTx.setText(sb.toString());
                   accountNameTx.setText("还款账号名：" + bank.getName());
                   accountTx.setText("还款账号：" +bank.getBankNo());
                   bankTx.setText("开户行：" +bank.getBankName());

             }
         });







     }

}
