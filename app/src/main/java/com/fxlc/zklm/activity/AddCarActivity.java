package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AddCarActivity extends BaseActivity implements View.OnClickListener{
     View brand;
    Context context;
    Dialog dialog;
    TextView brandTx;
    TextView carnoTx;

    GridView gridView;
    GridAdapter gridAdapter;

    StringBuffer sb = new StringBuffer();
    private static String[] Provinces = {"京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "新","苏", "浙", "赣", "鄂","桂",
            "甘", "晋", "蒙", "陕", "吉", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼","台"};
    private static String[] Nums = new String[10];
    private static String[] Chars = new String[24];
    private static List<String> characters = new ArrayList<>();
    int statu;
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
        setContentView(R.layout.activity_add_car);
        findViewById(R.id.getBrand).setOnClickListener(this);
        findViewById(R.id.getcarno).setOnClickListener(this);

        brandTx = (TextView) findViewById(R.id.brand);
        carnoTx = (TextView) findViewById(R.id.carno);
        findViewById(R.id.get_drive_license).setOnClickListener(this);
        findViewById(R.id.get_manage_license).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        initDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("品牌");
    }
    Intent it = new Intent();
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.getBrand:

                it.setClass(context,BrandActivity.class);
                startActivityForResult(it,101);

                break;

            case R.id.getcarno:
                dialog.show();

                break;
            case R.id.get_drive_license:

                it.setClass(context,PickImgActivity.class);
                startActivity(it);
//                Intent intent = new Intent(Intent.ACTION_PICK);
//              intent.setType("image/*");
//                startActivityForResult(intent, 100);

                break;
            case R.id.get_manage_license:

                Intent  intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1, 101);

                break;
            case R.id.next:
                 it.setClass(context,HandCarActivity.class);
                 startActivity(it);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            String brand = data.getStringExtra("brand");
            Log.d("cyd",brand);
            brandTx.setText(brand);
        }
    }

    private void initDialog() {
        dialog = new Dialog(this, R.style.dialog_alert_light);
        gridView = new GridView(this);
        gridView.setNumColumns(8);
        gridView.setBackgroundColor(Color.GRAY);
        gridView.setPadding(1, 1, 1, 1);
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -2);
        dialog.setContentView(gridView, params);
        gridAdapter = new GridAdapter();
        gridAdapter.setValues(Arrays.asList(Provinces));
        gridView.setAdapter(gridAdapter);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sb.append(gridAdapter.getItem(i));
                if (sb.length() == 0 && statu == 1){
                    gridView.setNumColumns(8);
                    gridAdapter.setValues(Arrays.asList(Provinces));
                    gridAdapter.notifyDataSetChanged();
                }else if (statu == 0){
                    statu = 1;
                    gridView.setNumColumns(7);
                    gridAdapter.setValues(characters);
                    gridAdapter.notifyDataSetChanged();
                }
               carnoTx.setText(sb.toString());

            }
        });
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
