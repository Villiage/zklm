package com.fxlc.zklm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.fxlc.zklm.R;
import com.fxlc.zklm.util.DisplayUtil;

public class GuideActivity extends Activity {

    ViewPager pager;
    int[] images = new int[4];
    ImageView[] dots = new ImageView[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(Window.FEATURE_NO_TITLE) ;
        DisplayUtil.translucentStatubar(this);
        setContentView(R.layout.activity_gilde);
        pager = (ViewPager) findViewById(R.id.pager);

        images[0] = R.drawable.y1;
        images[1] = R.drawable.y2;
        images[2] = R.drawable.y3;
        images[3] = R.drawable.y4;
        pager.setAdapter(new MPagerAdapter());
        ViewGroup dotGroup = (ViewGroup) findViewById(R.id.dots);
        for (int i = 0; i < dots.length; i++) {
            dots[i] = (ImageView) dotGroup.getChildAt(i);
        }
        dots[0].setSelected(true);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dots.length; i++) {
                  dots[i].setSelected(i == position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

       class MPagerAdapter  extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            ImageView iv = new ImageView(container.getContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1,-1);
            iv.setLayoutParams(lp);
            iv.setBackgroundResource(images[position]);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }
    };
}
