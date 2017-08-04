package com.fxlc.zklm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by cyd on 2017/7/27.
 */

public class ListActiviity extends BaseActivity{
    PtrFrameLayout ptrFrame;
    public ListView listView;
    public View dataEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ptrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        PtrClassicDefaultHeader header =  new PtrClassicDefaultHeader(this);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });



        listView = (ListView) findViewById(R.id.list);
        dataEmptyView = findViewById(R.id.data_empty);

//        dataEmptyView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadData();
//            }
//        });

    }

    public void loadData(){


    }
    public void showEmptyView() {

        if (dataEmptyView != null) {
            dataEmptyView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public void showDataView() {
        dataEmptyView = findViewById(R.id.data_empty);
        if (dataEmptyView != null) {
            dataEmptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

    }


}
