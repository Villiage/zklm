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
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Truck;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.CarService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

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


public class MycarActivity extends BaseActivity {
    private ViewPager mPager;
    private ListView[] pagerViews = new ListView[3];
    private TabLayout tabLayout;
    private Retrofit retrofit;

    List<Truck> noTrucks = new ArrayList<>();
    List<Truck> waitTrucks = new ArrayList<>();
    List<Truck> okTrucks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        tabLayout = (TabLayout) findViewById(R.id.tabcontainer);

        mPager = (ViewPager) findViewById(R.id.pager);
        tabLayout.setupWithViewPager(mPager);
        initView();
        mPager.setAdapter(new CarPagerAdapter());
        retrofit = MyApplication.getRetrofit();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        title("我的车辆");
    }

    public void initData() {
        proDialog.show();
        CarService service = retrofit.create(CarService.class);
        Call<ResponseBody> call = service.getTrucks();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                proDialog.dismiss();
                try {
                    String jsonStr = response.body().string();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("response",jsonStr);
                    JSONArray arr = jsonObj.getJSONObject("body").getJSONArray("carlist");
                    Gson gson = new Gson();
                    for (int i = 0; i < arr.length(); i++) {
                        Truck truck = gson.fromJson(arr.getString(i), Truck.class);
                        if (truck.getStatus() == 0 ) {
                            noTrucks.add(truck);
                        } else if (truck.getStatus()  == 1) {
                            waitTrucks.add(truck);
                        } else if (truck.getStatus()  == 2) {
                            okTrucks.add(truck);
                        }
                    }

                    ((ListAdapter)pagerViews[0].getAdapter()).setData(noTrucks,0);
                    ((ListAdapter)pagerViews[1].getAdapter()).setData(waitTrucks,1);
                    ((ListAdapter)pagerViews[2].getAdapter()).setData(okTrucks,2);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });


    }

    private void initView() {
        for (int i = 0;i < 3;i++) {
            ListView listView = new ListView(this);
            listView.setAdapter(new ListAdapter());
            listView.setDividerHeight(0);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    it.setClass(ctx, TruckInfoActivity.class);
                    it.putExtra("truck", ((ListAdapter) pagerViews[mPager.getCurrentItem()].getAdapter()).getItem(i));
                    startActivity(it);
                }
            });
            pagerViews[i] = listView;
        }
    }




    class ListAdapter extends BaseAdapter {
        int statu;
        List<Truck> data;

        public ListAdapter() {

        }

        public void setData(List<Truck> data,int status) {
            this.data = data;
            this.statu = status;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return  data == null? 0: data.size();
        }

        @Override
        public Truck getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Truck truck = data.get(i);
            if (statu == 0) {
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mycar_not, null);
                }

            } else if (statu == 1) {
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mycar_wait, null);
                }

            } else if (statu == 2) {
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mycar_ok, null);
                }
                TextView txt = (TextView) view.findViewById(R.id.amount);
                txt.setText(truck.getMortgageMoney());
            }
             TextView carNo = (TextView) view.findViewById(R.id.car_no);

            if (truck.getCartype() == 0)
                carNo.setText(truck.getCarNo());
            else if (truck.getCartype() == 1)
                carNo.setText(truck.getHandcarNo());
            return view;
        }
    }

    class CarPagerAdapter extends android.support.v4.view.PagerAdapter {

        String[] titles = new String[]{"未通过", "审核中", "已通过"};

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pagerViews.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(pagerViews[position]);
            return pagerViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }
}
