package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.ListActiviity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.MyCars;
import com.fxlc.zklm.bean.Truck;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.CarService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MycarActivityNew extends ListActiviity {


    List<Truck> trucks = new ArrayList<>();
    TruckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mycar_new);
        super.onCreate(savedInstanceState);

        listView.setAdapter(adapter =  new TruckAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                it.setClass(ctx, TruckInfoActivity.class);
                it.putExtra("truck", trucks.get(i));
                startActivity(it);
            }
        });
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        title("我的车辆");
    }

    public void loadData() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();
        CarService service = retrofit.create(CarService.class);
        Call<HttpResult<MyCars>> call = service.getTrucksNew();
        call.enqueue(new HttpCallback<MyCars>() {
            @Override
            public void onSuccess(MyCars myCars) {
                 proDialog.dismiss();
                 trucks = myCars.getCarlist();
                 if (trucks != null && !trucks.isEmpty()){

                     showDataView();
                     adapter.notifyDataSetChanged();

                 }else {
                     showEmptyView();
                 }
            }
        });


    }


    class TruckAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return trucks == null ? 0 : trucks.size();
        }

        @Override
        public Truck getItem(int i) {
            return trucks.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Truck truck = trucks.get(i);
            if (view == null)
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mycar, null);

            TextView carNo = (TextView) view.findViewById(R.id.car_no);
            TextView carStatu = (TextView) view.findViewById(R.id.car_statu);

            if (truck.getStatus() == 0){
              carStatu.setText("未通过");
            }else if (truck.getStatus() == 1){
                carStatu.setText("审核中");
                 carStatu.setTextColor(getResources().getColor(R.color.text_blue));
            }else if (truck.getStatus() == 2){
                carStatu.setTextColor(getResources().getColor(R.color.text_blue));
                carStatu.setText("通过");
            }
            if (truck.getCartype() == 0){
                carNo.setText(truck.getCarNo());

            } else if (truck.getCartype() == 1){
                carNo.setText(truck.getHandcarNo());

            }
            return view;
        }
    }


}
