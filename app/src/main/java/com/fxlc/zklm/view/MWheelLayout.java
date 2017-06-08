package com.fxlc.zklm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.fxlc.zklm.R;

/**
 * Created by cyd on 2017/3/23.
 */

public class MWheelLayout extends FrameLayout {
    private CycleWheelView mWheelView;
    private View markView;

    public MWheelLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MWheelLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MWheelLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final FrameLayout.LayoutParams itemP = new FrameLayout.LayoutParams(-1, 0);
        itemP.gravity = Gravity.CENTER_VERTICAL;
        markView = new View(getContext());
        markView.setBackgroundResource(R.drawable.line_stroke);
        addView(markView, itemP);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2);
        mWheelView = new CycleWheelView(getContext());
        mWheelView.setItemHeightLisener(new CycleWheelView.ItemHeightLisener() {
            @Override
            public void onMeasure(int itemHeight) {
                itemP.height  = itemHeight;
            }
        });
        addView(mWheelView, params);


    }






    public CycleWheelView getWheelView() {

        return mWheelView;
    }
}
