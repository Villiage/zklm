package com.fxlc.zklm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;

import java.util.ArrayList;
import java.util.List;

public class BrandActivity extends BaseActivity implements View.OnClickListener {
    List<String> brandList;
    ListView listView;
    ListView secListView;
    BrandAdapter brandAdapter;
    List<String> secList;
    SecAdapter secAdapter;
    String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_brand);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.TRANSPARENT);

        listView = (ListView) findViewById(R.id.list);
        initData();
        brandAdapter = new BrandAdapter(brandList);
        listView.setAdapter(brandAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("cyd", "click" + i);
                brand = brandList.get(i);
                if (!drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.openDrawer(Gravity.RIGHT);
                    initSecData();
                    secAdapter.setDataList(secList);

                }
            }
        });
        secListView = (ListView) findViewById(R.id.sec_list);
        secAdapter = new SecAdapter();
        secListView.setAdapter(secAdapter);
        secListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent();
                String result = secList.get(i);
                Log.d("cyd",result);
                it.putExtra("brand", result);
                setResult(RESULT_OK, it);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("品牌");
    }

    private void initData() {
        brandList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            brandList.add("一汽解放");
        }

    }

    private void initSecData() {
        secList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            secList.add("一汽解放J6P牵引车");
        }

    }


    class BrandAdapter extends BaseAdapter {
        private List<String> dataList;

        public BrandAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
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

            TextView txt1, txt2, charTxt;
            if (view == null) {
//                int id = android.R.layout.simple_list_item_2;
                int id = R.layout.item_brand;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
            }
            String brand = dataList.get(i);
            charTxt = (TextView) view.findViewById(R.id.firstchar);
            txt1 = (TextView) view.findViewById(R.id.text1);
            txt1.setText(brand);
            charTxt.setText("A");
            if (i > 0 && brand == dataList.get(i - 1)) {
                charTxt.setVisibility(View.GONE);
            } else charTxt.setVisibility(View.VISIBLE);

            return view;
        }
    }

    class SecAdapter extends BaseAdapter {
        private List<String> dataList;

        public void setDataList(List<String> dataList) {
            this.dataList = dataList;
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

            TextView txt1, txt2, charTxt;
            if (view == null) {
//                int id = android.R.layout.simple_list_item_2;
                int id = R.layout.item_brand_sec;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
            }


            return view;
        }
    }

}
