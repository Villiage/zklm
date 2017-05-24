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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fxlc.zklm.R;

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

}
