package com.fxlc.zklm;


import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private FragmentHelper fragmentHelper = new FragmentHelper();
    private List<Fragment> frags = new ArrayList<>();
    private ViewGroup navGroup;
    private View fab;
    private int animaDuration = 200;
    private View floatView, pubCars, pubGoods;

    private ValueAnimator animator;

    public void initFrags() {
        frags.add(fragmentHelper.getItem(0));
        frags.add(fragmentHelper.getItem(1));
        frags.add(fragmentHelper.getItem(2));
        frags.add(fragmentHelper.getItem(3));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.home_bg));
        initFrags();
        switchContent(frags.get(0),null);

        fab =  findViewById(R.id.fab);
        floatView = findViewById(R.id.floating);
        floatView.setAlpha(0);
        floatView.setVisibility(View.GONE);
        floatView.setOnClickListener(this);

        pubCars = findViewById(R.id.pub_cars);
        pubGoods = findViewById(R.id.pub_goods);

        animator = new ValueAnimator();
        animator.setDuration(animaDuration);
        animator.setIntValues(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                pubCars.setScaleX(valueAnimator.getAnimatedFraction());
                pubCars.setScaleY(valueAnimator.getAnimatedFraction());
                pubGoods.setScaleX(valueAnimator.getAnimatedFraction());
                pubGoods.setScaleY(valueAnimator.getAnimatedFraction());
                fab.setRotation(-valueAnimator.getAnimatedFraction() * 45);
                floatView.setAlpha( valueAnimator.getAnimatedFraction());
            }
        });

        fab.setOnClickListener(this);
        navGroup = (ViewGroup) findViewById(R.id.nav_group);
        for (int i = 0; i < navGroup.getChildCount(); i++) {
            navGroup.getChildAt(i).setOnClickListener(this);
        }
       findViewById(R.id.nav_home).setSelected(true);

    }

    private Fragment mContent;

    /**
     * 修改显示的内容 不会重新加载
     **/
    public void switchContent(Fragment to,View view)

    {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if (mContent == null) {

            transaction.add(R.id.content, to).commit();
        } else if (mContent != to) {
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }

        }
        mContent = to;
        resetAnim();
        if(view != null) checkTab(view.getId());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.nav_home:

                switchContent(frags.get(0),view);
                break;
            case R.id.nav_cars:
                switchContent(frags.get(1),view);
                break;
            case R.id.nav_goods:
                switchContent(frags.get(2),view);
                break;
            case R.id.nav_ucenter:
                switchContent(frags.get(3),view);
                break;
            case R.id.fab:

                if (fab.getRotation() == 0) {
                    floatView.setVisibility(View.VISIBLE);
                    animator.start();
                } else {
                    floatView.setVisibility(View.GONE);
                    animator.reverse();
                }
                navGroup.post(new Runnable() {
                    @Override
                    public void run() {
                     navGroup.requestFocus();
                    }
                });
                break;
            case R.id.floating:
                resetAnim();
                break;
        }
    }

    private void checkTab(int checkId) {
        for (int i = 0; i < navGroup.getChildCount(); i++) {
            View v = navGroup.getChildAt(i);
            v.setSelected(v.getId() == checkId);
        }
    }
    public void resetAnim(){

        if (fab!= null && fab.getRotation() != 0) {
            animator.reverse();
            floatView.setVisibility(View.GONE);
        }
    }

}
