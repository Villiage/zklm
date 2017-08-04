package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.ListActiviity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.LoanHistory;
import com.fxlc.zklm.bean.PayHistory;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.PayService;
import com.fxlc.zklm.util.DialogUtil;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;

public class BorrowHistoryActivity extends ListActiviity implements View.OnClickListener {

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
        setContentView(R.layout.activity_borrow_history);
        super.onCreate(savedInstanceState);


        adapter = new MAdapter();
        listView.setAdapter(adapter);

        curDateTx = (TextView) findViewById(R.id.curdate);
        (calendarV = findViewById(R.id.selDate)).setOnClickListener(this);

        mYear = calendar.get(Calendar.YEAR) + "";
        mMonth = (calendar.get(Calendar.MONTH) + 1) + "";
        curDateTx.setText(mYear + "年" + mMonth + "月");

        initDialog();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("贷款明细");


    }

    @Override
    public void loadData() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();

        PayService service = retrofit.create(PayService.class);

        Call<HttpResult<LoanHistory>> call = service.loanList(mYear, mMonth);

        call.enqueue(new HttpCallback<LoanHistory>() {
            @Override
            public void onSuccess(LoanHistory loanHistory) {
                proDialog.dismiss();
                List<ListBean> dataList = new ArrayList<ListBean>();
                List<LoanHistory.LoanBean> loanList = loanHistory.getLoanlist();
                if (loanList != null && loanList.size() > 0) {
                    for (LoanHistory.LoanBean loanBean : loanList) {
                        ListBean loan = new ListBean();
                        loan.setId(loanBean.getId());
                        loan.setLabel("借款");
                        loan.setAmount(loanBean.getMoney());
                        loan.setDate(loanBean.getLoantime());
                        dataList.add(loan);
//                      bean.setStatu(loanBean.getStatus());
                        List<LoanHistory.LoanBean.RepayBean> repayList = loanBean.getRepaylist();
                        for (LoanHistory.LoanBean.RepayBean repayBean : repayList) {
                            ListBean repay = new ListBean();
                            repay.setId(repayBean.getId());
                            repay.setLabel("还款");
                            repay.setAmount(repayBean.getRepaymoney());
                            repay.setDate(repayBean.getRepaytime());
                            dataList.add(repay);
                        }

                    }
                    showDataView();
                    adapter.setDataList(dataList);
                } else {
                    showEmptyView();
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
                proDialog.dismiss();
            }
        });

    }

    private void  initDialog(){

        d = DialogUtil.createDateDialog(this, Arrays.asList(yArr), Arrays.asList(mArr), new DialogUtil.DateSelLisener() {
            @Override
            public void onSel(String year, String month) {
                d.dismiss();
                mYear = year;
                mMonth = month;
                curDateTx.setText(mYear + "年" + mMonth + "月");
                loadData();

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
        private List<ListBean> dataList;

        public void setDataList(List<ListBean> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public ListBean getItem(int i) {
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
            ListBean item = getItem(i);
            holder.labelTx.setText(item.getLabel());
            holder.dateTx.setText(item.getDate());
            holder.amountTx.setText(item.getAmount());
            return view;
        }
    }

    class ItemHolder {
        TextView labelTx, dateTx, amountTx;

    }

    class ListBean {
        String label;
        String id;
        String amount;
        String statu;
        String date;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatu() {
            return statu;
        }

        public void setStatu(String statu) {
            this.statu = statu;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
