package com.fxlc.zklm.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.MediaStoreData;
import com.fxlc.zklm.bean.PayInfo;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.PayService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PayInfoActivity extends BaseActivity implements View.OnClickListener {
    ListView listView;
    MAdapter adapter;
    TextView picSizeTx;
    TextView fromName, toName, fromAcount, toAcount;
    TextView amountTxt, remark;
    String payId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_info);
        payId = getIntent().getStringExtra("payId");
        listView = (ListView) findViewById(R.id.list);
        picSizeTx = (TextView) findViewById(R.id.picsize);
        fromName = (TextView) findViewById(R.id.fromName);
        fromAcount = (TextView) findViewById(R.id.fromAcount);
        toName = (TextView) findViewById(R.id.toName);
        toAcount = (TextView) findViewById(R.id.toAcount);
        amountTxt = (TextView) findViewById(R.id.payAmount);

        adapter = new MAdapter();
        listView.setAdapter(adapter);
        setValue();
        loadData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("详情");
    }

    private void setValue() {

        fromAcount.setText(user.getMobile());
        fromName.setText(user.getName());


    }

    private void loadData() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();

        PayService service = retrofit.create(PayService.class);

        Call<HttpResult<PayInfo>> call = service.payInfo(payId);


        call.enqueue(new HttpCallback<PayInfo>() {
            @Override
            public void onSuccess(PayInfo payInfo) {
                proDialog.dismiss();

                toAcount.setText(payInfo.getPayview().getIncomeMobile());
                toName.setText(payInfo.getPayview().getIncomeUser());

                amountTxt.setText(payInfo.getPayview().getPaymentMoney());
                picSizeTx.setText("共"+ payInfo.getPayview().getBillImg().size() + "张");
                adapter.addData(payInfo.getPayview().getBillImg());

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

    class MAdapter extends BaseAdapter {
        private ArrayList<String> dataList;


        public ArrayList<String> getDataList() {
            return dataList;
        }

        public void addData(List<String> dataList) {
            if (this.dataList == null) this.dataList = new ArrayList<>();
            this.dataList.addAll(0, dataList);
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public String getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String path = getItem(i);
            ImageView img;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_billimg,null);
            }
            img = (ImageView) view.findViewById(R.id.img);
            Glide.with(ctx).load(path).fitCenter().into(img);

            return view;
        }
    }

    class RotateTransformation extends BitmapTransformation{

        private int rotateRotationAngle = 0;


        public RotateTransformation(Context context,int rotateRotationAngle) {
            super(context);
            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {


            return TransformationUtils.rotateImage(toTransform,rotateRotationAngle);


        }

        @Override
        public String getId() {
            return "rotate" + rotateRotationAngle;
        }
    }
}
