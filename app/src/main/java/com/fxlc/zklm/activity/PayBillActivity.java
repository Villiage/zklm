package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxlc.zklm.R;
import com.fxlc.zklm.util.DisplayUtil;

public class PayBillActivity extends AppCompatActivity {
    private String[] gridvalue = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "del"};
    private int gridPadding;
    private int gridItemH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);
        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payDialog.show();
            }
        });
        initDialog();
        closeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payDialog.dismiss();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 9 && i !=11){
                    String num = gridvalue[i];
                    int n = 0;
                    while (n < 6) {
                        if (TextUtils.isEmpty(psdTxt[n].getText())) {
                            psdTxt[n].setText(num);
                            break;
                        }
                        n++;
                    }
                }else if(i == 11){
                    int n = 5;
                    while (n >= 0) {
                        if (!TextUtils.isEmpty(psdTxt[n].getText())) {
                            psdTxt[n].setText("");
                            break;
                        }
                        n--;
                    }

                }



            }
        });
    }

    Dialog payDialog;
    TextView[] psdTxt = new TextView[6];
    GridView gridView;
    View closeTxt;
    private void initDialog() {
        gridPadding = DisplayUtil.dp2px(this, 10);
        gridItemH = DisplayUtil.dp2px(this, 50);
        payDialog = new Dialog(this, R.style.dialog_alert);
        payDialog.setContentView(R.layout.dialog_pay);
        for (int i = 0; i < 6; i++) {
            psdTxt[i] = (TextView) payDialog.findViewById(getResources().getIdentifier("tv_pass" + (i + 1), "id", getPackageName()));
        }
        closeTxt = payDialog.findViewById(R.id.close);
        gridView = (GridView) payDialog.findViewById(R.id.number);
        gridView.setBackgroundColor(Color.LTGRAY);
        gridView.setAdapter(new NumberAdapter());
        Window win = payDialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

    }

    class NumberAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return gridvalue.length;
        }

        @Override
        public Object getItem(int i) {
            return gridvalue[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (i < 11) {
                TextView textView = new TextView(viewGroup.getContext());
                textView.setText(gridvalue[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setMinimumHeight(gridItemH);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setTextColor(Color.BLACK);
                if (i == 9) textView.setBackgroundColor(Color.LTGRAY);
                else textView.setBackgroundColor(getResources().getColor(R.color.white));
                return textView;
            } else {
                ImageView img = new ImageView(viewGroup.getContext());
                img.setMinimumHeight(gridItemH);
                img.setScaleType(ImageView.ScaleType.CENTER);
                img.setImageResource(R.drawable.nine_del);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = GridLayout.LayoutParams.MATCH_PARENT;
                params.width = GridLayout.LayoutParams.MATCH_PARENT;
                img.setLayoutParams(params);
                return img;
            }


        }
    }
}
