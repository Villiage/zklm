package com.fxlc.zklm.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Truck;
import com.fxlc.zklm.frag.HandTruckFrag;
import com.fxlc.zklm.frag.MainTruckFrag;

public class TruckInfoActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager mPager;
    private TabLayout tabLayout;
    private FragmentPagerAdapter pagerAdapter;
    private MainTruckFrag mainTruckFrag;
    private HandTruckFrag handTruckFrag;
    private Truck truck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        truck = (Truck) getIntent().getSerializableExtra("truck");
        setContentView(R.layout.activity_truck_info);
        tabLayout = (TabLayout) findViewById(R.id.tabcontainer);

        mPager = (ViewPager) findViewById(R.id.pager);
        tabLayout.setupWithViewPager(mPager);
        initData();
        pagerAdapter = new TruckFragAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

    }

    private void initData() {

        mainTruckFrag =  MainTruckFrag.newInstance(truck);
        handTruckFrag =  HandTruckFrag.newInstance(truck);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

    class TruckFragAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"主车", "挂车"};
        public TruckFragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return mainTruckFrag;
            else return handTruckFrag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
