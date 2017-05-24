package com.fxlc.zklm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyd on 2017/3/22.
 */

public class MWheelView extends ScrollView {
    public static final String TAG = "MWheelView";
    public static final int showCount = 5;
    private int offset = showCount / 2;
    private int freeScrollTime = 200;//ms
    private LinearLayout linearView;

    private int itemHeight;
    private int selectIndex = 0;
    private List<String> mData;

    public MWheelView(Context context) {
        super(context);
        init();
    }

    public MWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mData = new ArrayList<>();
        setVerticalScrollBarEnabled(false);
        linearView = new LinearLayout(getContext());
        linearView.setOrientation(LinearLayout.VERTICAL);
        addView(linearView);
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }

    public int getItemHeight() {
        return itemHeight;
    }
    public void setItems(List<String> data){
          mData.addAll(data);
          for(int i = 0;i < offset;i ++){
              mData.add("");
              mData.add(0,"");
          }
          setData(mData);
    }
    public void setData(List<String> data) {
        for (String str : data) {
            linearView.addView(createItem( str));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        itemHeight = linearView.getChildAt(0).getMeasuredHeight();
        int height = itemHeight * showCount;
        setMeasuredDimension(widthMeasureSpec, height);

    }

    private TextView createItem(String str) {
        TextView txt = new TextView(getContext());
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(10, 10, 10, 10);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        txt.setText(str);

        return txt;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        refreshItemView(t);
    }

    private void refreshItemView(int y) {
        int position = y / itemHeight + offset;
        int remainder = y % itemHeight;

        if (remainder > itemHeight / 2) {
            position = position + 1;
        }
        for (int i = position - offset; i <= position + offset; i++) {
            TextView itemView = (TextView) linearView.getChildAt(i);
            if (position == i) {
                itemView.setTextColor(Color.parseColor("#00fa00"));
            } else {
                itemView.setTextColor(Color.parseColor("#999999"));
            }
        }


    }

    public void setSelectIndex(int position) {
        int scrollHeight = itemHeight * position;
        smoothScrollTo(0, scrollHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    adjust();
                }
            }, freeScrollTime);
        }
        return super.onTouchEvent(ev);
    }


    private void adjust() {
        int y = getScrollY();
        int position = y / itemHeight;
        int remainder = y % itemHeight;

        if (remainder > itemHeight / 2) {
            position = position + 1;
        }
        int scrollHeight = itemHeight * position;
        smoothScrollTo(0, scrollHeight);
        scrollTo(0, scrollHeight);
        if (listener != null) {
            listener.onSelect(position);
        }
    }

    private OnSelectListener listener;

    public interface OnSelectListener {
        void onSelect(int position);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;

    }
}
