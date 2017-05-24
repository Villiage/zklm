package com.fxlc.zklm.test;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.fxlc.zklm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CarNoActivity extends AppCompatActivity {
    EditText ed;
    Dialog dialog1;
    GridView gridView;
    GridAdapter adapter;
    Context context;
    int level = 0;
    private static String[] Provinces = {"京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "新", "浙", "赣", "桂",
            "甘", "晋", "蒙", "陕", "吉", "闵", "贵", "粤", "青", "藏", "川", "宁", "琼"};
    private static String[] Nums = new String[10];
    private static String[] Chars = new String[24];
    private static List<String> characters = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            Nums[i] = String.valueOf(i);
        }
        int index = 0;
        for (char i = 'A'; i <= 'Z'; i++) {

            if (i != 'I' && i != 'O') {
                Chars[index] = String.valueOf(i);
                index++;
            }
        }
        Iterator<String> iterator1 = Arrays.asList(Nums).iterator();
        Iterator<String> iterator2 = Arrays.asList(Chars).iterator();
        for (int i = 0; i < Nums.length + Chars.length; i++) {
            if (i % 7 < 2) {
                characters.add(iterator1.next());
            } else {
                characters.add(iterator2.next());
            }

        }
        characters.add("Del");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_car_no);
        initDialog();
        ed = (EditText) findViewById(R.id.carno);

        InputFilter.LengthFilter inputFilter = new InputFilter.LengthFilter(7);
        ed.setFilters(new InputFilter.LengthFilter[]{inputFilter});
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialog1.isShowing()) {
                    openNoSelDialog();

                }
            }
        });
    }


    private void initDialog() {
        dialog1 = new Dialog(this, R.style.dialog_alert_light);
        gridView = new GridView(this);
        gridView.setNumColumns(8);
        gridView.setBackgroundColor(Color.GRAY);
        gridView.setPadding(1, 1, 1, 1);
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
        dialog1.setContentView(gridView, params);
        adapter = new GridAdapter();
//        gridView.setAdapter(adapter);
        Window win = dialog1.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (level == 1 && i == adapterView.getCount() - 1) {
                    int lengh = ed.getText().length();
                    if (lengh >= 0)
                        ed.getEditableText().delete(lengh - 1, lengh);
                } else
                    ed.setText(ed.getText() + adapter.getItem(i));
                if (level == 0) {
                    gridView.setNumColumns(7);
                    adapter.setValues(characters);
                    adapter.notifyDataSetChanged();
                }
                level = 1;

            }
        });
    }


    public void openNoSelDialog() {

        dialog1.show();

        if (ed.getText().toString().trim().equals("")) {
            adapter.setValues(Arrays.asList(Provinces));
            level = 1;
        } else {
            adapter.setValues(characters);

        }
        adapter.notifyDataSetChanged();


    }

    class GridAdapter extends BaseAdapter {
        List<String> values;

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public String getItem(int i) {
            return values.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null)
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_list_item, viewGroup, false);
            TextView txt = (TextView) view;
            txt.setBackgroundColor(getResources().getColor(R.color.white));
            txt.setText(values.get(i));
            return view;
        }
    }
}
