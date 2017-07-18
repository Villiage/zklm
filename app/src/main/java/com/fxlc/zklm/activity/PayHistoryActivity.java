package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.PayHistory;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.PayService;
import com.fxlc.zklm.util.DialogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PayHistoryActivity extends BaseActivity implements View.OnClickListener {
    ListView listView;
    MAdapter adapter;
    List<String> dataList = new ArrayList<>();
    Dialog d;
    String[] yArr = {"2015", "2016", "2017", "2018", "2019"};
    String[] mArr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    View calendarV;
    String mYear, mMonth;
    TextView curDateTx;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_history);
        listView = (ListView) findViewById(R.id.list);
        curDateTx = (TextView) findViewById(R.id.curdate);
        (calendarV = findViewById(R.id.selDate)).setOnClickListener(this);
        adapter = new MAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent();
                it.setClass(ctx,PayInfoActivity.class);
                it.putExtra("payId",adapter.getItem(i).getId());
                startActivity(it);
            }
        });
        loadData();
        d = DialogUtil.createDateDialog(this, Arrays.asList(yArr), Arrays.asList(mArr), new DialogUtil.DateSelLisener() {
            @Override
            public void onSel(String year, String month) {
                d.dismiss();
                mYear = year;
                mMonth = month;
                loadData();

            }
        });
        mYear = calendar.get(Calendar.YEAR) + "";
        mMonth = (calendar.get(Calendar.MONTH) + 1) + "";

        curDateTx.setText(mYear + "年" + mMonth + "月");
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("支付明细");
    }

    private void loadData() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();

        PayService service = retrofit.create(PayService.class);

        Call<HttpResult<PayHistory>> call = service.payList(mYear, mMonth);

        call.enqueue(new HttpCallback<PayHistory>() {
            @Override
            public void onSuccess(PayHistory payDetail) {
                proDialog.dismiss();
                adapter.setDataList(payDetail.getPaylist());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
                proDialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selDate:
                d.show();

                break;


        }
    }

    class MAdapter extends BaseAdapter {
        private List<PayHistory.PayItem> dataList;

        public void setDataList(List<PayHistory.PayItem> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public PayHistory.PayItem getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


            ItemHolder holder = null;
            if (view == null) {
                holder = new ItemHolder();
                int id = R.layout.item_pay_record;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
                holder.labelTx = (TextView) view.findViewById(R.id.type);
                holder.dateTx = (TextView) view.findViewById(R.id.date);
                holder.amountTx = (TextView) view.findViewById(R.id.amount);
                view.setTag(holder);
            } else holder = (ItemHolder) view.getTag();
            PayHistory.PayItem item = getItem(i);
            holder.labelTx.setText(item.getIncomeUser());
            holder.dateTx.setText(item.getPayTime());
            holder.amountTx.setText("-" + item.getPaymentMoney());
            return view;
        }
    }

    class ItemHolder {
        TextView labelTx, dateTx, amountTx;

    }
}
