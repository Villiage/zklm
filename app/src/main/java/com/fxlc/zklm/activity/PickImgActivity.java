package com.fxlc.zklm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.MediaStoreData;
import com.fxlc.zklm.loader.ImageLoader;

import java.util.ArrayList;


public class PickImgActivity extends BaseActivity {
    private GridView gridView;
    private ArrayList<MediaStoreData> dataList;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<MediaStoreData> selList = new ArrayList<>();
    private int maxSize = 6;
    private View okView;
    private TextView tipTxt;
    private  String tip = "已选择*/6张";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        context = this;

        tipTxt = (TextView) findViewById(R.id.seltip);
        okView = findViewById(R.id.ok);
        gridView = (GridView) findViewById(R.id.gird);
        dataList = ImageLoader.loadAllImg(this);
        gridView.setAdapter(new MAdapter());
        final Intent it = new Intent();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                it.setClass(context, PhotoActivity.class);
                it.putParcelableArrayListExtra("localImg", dataList);
                it.putExtra("position", i);
                startActivity(it);
            }
        });
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.putParcelableArrayListExtra("selList",selList);
                setResult(100,it);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("相机照片");
    }

    class MAdapter extends BaseAdapter {

        MAdapter() {

            inflater = LayoutInflater.from(PickImgActivity.this);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = inflater.inflate(R.layout.item_media_img, viewGroup, false);
                holder = new ViewHolder();
                holder.img = (ImageView) view.findViewById(R.id.img);
                holder.mark = (ImageView) view.findViewById(R.id.mark);
                holder.mark.setOnClickListener(ocl);
                view.setTag(holder);
            } else holder = (ViewHolder) view.getTag();
            MediaStoreData data = dataList.get(i);
            holder.mark.setTag(i);
            holder.mark.setSelected(data.statu);
            Glide.with(context).load(data.uri).centerCrop().crossFade().into(holder.img);

            return view;
        }

        class ViewHolder {

            ImageView img;
            ImageView mark;
        }

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView img = (ImageView) view;
                int i = (int) view.getTag();
                MediaStoreData data = dataList.get(i);
                if (selList.size() < maxSize) {
                    img.setSelected(data.statu = !data.statu);
                    if (data.statu)
                        selList.add(data);
                    else selList.remove(data);
                    Log.d("cyd","size" + selList.size());
                }else{
                   if (data.statu){
                       img.setSelected(data.statu = false);
                       selList.remove(data);
                   }
                     else Toast.makeText(context,"最多能选择"+ maxSize+ "个",Toast.LENGTH_SHORT).show();
                }

                tipTxt.setText(tip.replace("*",selList.size()+ ""));
            }
        };
    }

}
