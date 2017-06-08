package com.fxlc.zklm.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;

import java.util.ArrayList;
import java.util.List;

public class PayDetailActivity extends BaseActivity {
   ListView listView;
    MAdapter adapter;
    List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        listView = (ListView) findViewById(R.id.list);
        adapter = new MAdapter();
        listView.setAdapter(adapter);
        initData();
        adapter.setDataList(dataList);

    }

    private void initData(){

         for (int i=0;i < 10;i++){

             dataList.add(i+"");

         }
    }
    class MAdapter extends BaseAdapter {
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
