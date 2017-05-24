package com.fxlc.zklm.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;


public class MycarActivity extends BaseActivity {
    private ViewPager mPager;
    private View[] pagerViews = new View[3];
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        tabLayout = (TabLayout) findViewById(R.id.tabcontainer);

        mPager = (ViewPager) findViewById(R.id.pager);
        tabLayout.setupWithViewPager(mPager);
        initData();
        mPager.setAdapter(new CarPagerAdapter());

    }
    private void initData(){
        for (int i = 0 ;i < 3;i++) {
            ListView listView = new ListView(this);
            listView.setAdapter(new ListAdapter());
            pagerViews[i] = listView;
        }
    }
    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
               if (view == null){

                   view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mycar,null);
               }

            return  view;
        }
    }
    class  CarPagerAdapter extends android.support.v4.view.PagerAdapter{

        String [] titles = new String[]{"未通过","审核中","已通过"};
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
