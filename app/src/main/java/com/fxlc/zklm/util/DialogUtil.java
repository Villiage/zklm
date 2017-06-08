package com.fxlc.zklm.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DrawableUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fxlc.zklm.R;
import com.fxlc.zklm.view.CycleWheelView;
import com.fxlc.zklm.view.MWheelLayout;
import com.fxlc.zklm.view.MWheelView;

import java.util.List;

/**
 * Created by cyd on 2017/4/19.
 */

public class DialogUtil {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Dialog createDialog(Context context, String[] items, View.OnClickListener ocl) {

        final Dialog d = new Dialog(context, R.style.dialog_alert);

        LinearLayout linear = new LinearLayout(context);
        linear.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < items.length; i++) {
            TextView textView = new TextView(context);
            textView.setTag(i);
            textView.setOnClickListener(ocl);
            textView.setGravity(Gravity.CENTER);
            textView.setText(items[i]);
            textView.setPadding(0, DisplayUtil.dp2px(context, 10), 0, DisplayUtil.dp2px(context, 10));
            textView.setBackground(DrawableUtil.createSelecotorDrawable(5));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            if (i < items.length - 1) params.bottomMargin = 5;
            linear.addView(textView, params);


        }
        TextView textView = new TextView(context);
        textView.setId(R.id.dialog_close);
        textView.setGravity(Gravity.CENTER);
        textView.setText("取消");
        textView.setPadding(0, DisplayUtil.dp2px(context, 10), 0, DisplayUtil.dp2px(context, 10));
        textView.setBackground(DrawableUtil.createSelecotorDrawable(0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.topMargin = 20;
        linear.addView(textView, params);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        d.setContentView(linear);

        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.y = 10;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        return d;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Dialog createSelDialog(Context context, String[] items, View.OnClickListener ocl) {

        final Dialog d = new Dialog(context, R.style.dialog_alert);
        int pad =  DisplayUtil.dp2px(context, 10);
        LinearLayout linear = new LinearLayout(context);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setBackgroundColor(context.getResources().getColor(R.color.divider));
        TextView cancelView = new TextView(context);
        cancelView.setPadding(pad,pad,pad,pad);
        cancelView.setText("取消");
        cancelView.setId(R.id.dialog_close);


        TextView okView = new TextView(context);
        okView.setPadding(pad,pad,pad,pad);
        okView.setText("确定");
        okView.setTextColor(context.getResources().getColor(R.color.text_blue));
        okView.setId(R.id.dialog_ok);

        FrameLayout.LayoutParams p1 = new FrameLayout.LayoutParams(-2,-2);
        FrameLayout.LayoutParams p2 = new FrameLayout.LayoutParams(-2,-2);
        p2.gravity= Gravity.RIGHT;
        FrameLayout top = new FrameLayout(context);
        top.setBackgroundColor(Color.WHITE);
        top.addView(cancelView,p1);
        top.addView(okView,p2);

        LinearLayout.LayoutParams topParam = new LinearLayout.LayoutParams(-1, -2);
        linear.addView(top,topParam);

        for (int i = 0; i < items.length; i++) {
            TextView textView = new TextView(context);
            textView.setTag(i);
            textView.setOnClickListener(ocl);
            textView.setGravity(Gravity.CENTER);
            textView.setText(items[i]);
            textView.setPadding(0, DisplayUtil.dp2px(context, 10), 0, DisplayUtil.dp2px(context, 10));
            textView.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
             params.topMargin = 1;
            linear.addView(textView, params);

        }


        d.setContentView(linear);

        Window win = d.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        return d;
    }
       public static Dialog createWheelDialog(Context context, List<String> list){
           Dialog d = new Dialog(context, R.style.dialog_alert);

           d.setContentView(R.layout.dialog_wheel);
           CycleWheelView wheelView1 = (CycleWheelView) d.findViewById(R.id.wheel1);
           wheelView1.setCycleEnable(false);
           wheelView1.setData(list);

           CycleWheelView wheelView2 = (CycleWheelView) d.findViewById(R.id.wheel2);
           wheelView2.setCycleEnable(false);
           wheelView2.setData(list);

           Window win = d.getWindow();
           WindowManager.LayoutParams lp = win.getAttributes();
           lp.gravity = Gravity.BOTTOM;

           lp.width = WindowManager.LayoutParams.MATCH_PARENT;
           lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
           win.setAttributes(lp);

           return d;
       }
}
