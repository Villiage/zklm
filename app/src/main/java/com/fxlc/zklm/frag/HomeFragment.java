package com.fxlc.zklm.frag;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fxlc.zklm.R;
import com.fxlc.zklm.ToolFragmentHelper;
import com.fxlc.zklm.util.DisplayUtil;
import com.fxlc.zklm.view.MInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyd on 2017/3/21.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ViewPager bannerPager;

    private View animHat, animWheel;
    private View tabBtn1, tabBtn2, tabBtn3;
    private int screenWidth;

    private ValueAnimator valueAnimator;
    private int animHatWidth, animWheelWidth;
    private int initHatTransX,initWheelTransX;
    private final static int AnimDuration = 500;
    private ImageView[] indicators;
    private View topPannel;
    private List<Integer> bannerImgs = new ArrayList<>();
    private int curPosition = 0;

    private ToolFragmentHelper toolHelper = new ToolFragmentHelper();
    private ViewPager toolPager;
    public HomeFragment() {
    }

    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        topPannel = view.findViewById(R.id.topPanel);
        ViewGroup dotGroup = (ViewGroup) view.findViewById(R.id.indicator);
        indicators = new ImageView[dotGroup.getChildCount()];
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = (ImageView) dotGroup.getChildAt(i);
        }

        bannerPager = (ViewPager) view.findViewById(R.id.banner);
        bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicators.length; i++) {
                    ImageView v = indicators[i];
                    v.setImageResource(i == position ? R.drawable.banner_dot_long : R.drawable.banner_dot);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        animHat = view.findViewById(R.id.anim_hat);
        animWheel = view.findViewById(R.id.anim_wheel);
        tabBtn1 = view.findViewById(R.id.btn1);
        tabBtn2 = view.findViewById(R.id.btn2);
        tabBtn3 = view.findViewById(R.id.btn3);

        tabBtn1.setOnClickListener(this);
        tabBtn2.setOnClickListener(this);
        tabBtn3.setOnClickListener(this);
        topPannel.setBackgroundResource(R.drawable.bg_gradient);

        toolPager  = (ViewPager) view.findViewById(R.id.tool_pager);

        toolPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                int unit = screenWidth/3;
//                float x =  (position + positionOffset)* unit;
//                animHat.setTranslationX(x + initHatTransX);
//                animWheel.setTranslationX(x + initWheelTransX);
//                animWheel.setRotation((position + positionOffset) *360);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "page" +  position);
                 animate(position);
                curPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
     class  ToolFragAdapter extends FragmentPagerAdapter{


         public ToolFragAdapter(FragmentManager fm) {
             super(fm);
         }

         @Override
         public Fragment getItem(int position) {
             return toolHelper.getItem(position);
         }

         @Override
         public int getCount() {
             return 3;
         }

     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         initAnim();
        bannerImgs.add(R.drawable.banner1);
        bannerImgs.add(R.drawable.banner2);
        bannerImgs.add(R.drawable.banner3);
        bannerPager.setAdapter(new BannderAdapter());

        toolPager.setAdapter(new ToolFragAdapter(getChildFragmentManager()));

    }

    private void initAnim(){


        screenWidth = getResources().getDisplayMetrics().widthPixels;
        animHatWidth = DisplayUtil.measureSize(animHat, DisplayUtil.Size.with);
        animWheelWidth = DisplayUtil.measureSize(animWheel, DisplayUtil.Size.with);

        initHatTransX = screenWidth / 6 - animHatWidth / 2;
        initWheelTransX = screenWidth / 6 - animWheelWidth / 2;
        animHat.setTranslationX(initHatTransX);
        animWheel.setTranslationX(initWheelTransX);

        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(AnimDuration);
        valueAnimator.setInterpolator(new MInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                animHat.setTranslationX((int)animator.getAnimatedValue("translateX")  +  initHatTransX);
                animWheel.setTranslationX((int)animator.getAnimatedValue("translateX") + initWheelTransX);
                animWheel.setRotation((Integer) animator.getAnimatedValue("rotation"));


            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                toolPager.setCurrentItem(0,true);

                break;
            case R.id.btn2:

                toolPager.setCurrentItem(1,true);

                break;
            case R.id.btn3:

                toolPager.setCurrentItem(2,true);

                break;

        }
    }
        public void animate(int position){
            PropertyValuesHolder trans =  PropertyValuesHolder.ofInt("translateX",screenWidth/3 * curPosition , screenWidth/3 * position);
            PropertyValuesHolder rotation =  PropertyValuesHolder.ofInt("rotation",360 * curPosition , 360 * position);
            valueAnimator.setValues(trans,rotation);
            valueAnimator.start();
        }
    class BannderAdapter extends PagerAdapter {
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bannerImgs.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            ImageView iv = new ImageView(container.getContext());
            iv.setBackgroundResource(bannerImgs.get(position));
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }
    }


}
