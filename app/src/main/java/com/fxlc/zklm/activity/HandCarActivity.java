package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.R;
import com.fxlc.zklm.util.DialogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandCarActivity extends BaseActivity implements View.OnClickListener {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_handcar);

        findViewById(R.id.get_type).setOnClickListener(this);
        findViewById(R.id.get_long).setOnClickListener(this);
        findViewById(R.id.get_height).setOnClickListener(this);
        findViewById(R.id.get_no).setOnClickListener(this);
        findViewById(R.id.get_drive_license).setOnClickListener(this);
        findViewById(R.id.get_manage_license).setOnClickListener(this);

    }

    Dialog d;
    String[] typeArr = {"仓栅式半挂车", "栏板式半挂车", "平板式半挂车"};
    String[] longArr = {"1", "2", "3", "4", "5"};

    @Override
    protected void onResume() {
        super.onResume();
        title("挂车");
    }

    Intent it = new Intent();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_type:

                d = DialogUtil.createSelDialog(this, typeArr, this);
                d.show();
                break;
            case R.id.get_long:

                d = DialogUtil.createWheelDialog(this, Arrays.asList(longArr));
                d.show();
                break;
            case R.id.get_height:

                d = DialogUtil.createWheelDialog(this, Arrays.asList(longArr));
                d.show();
                break;
            case R.id.get_drive_license:

                it.setClass(context, PickImgActivity.class);
                startActivityForResult(it, 100);

                break;
            case R.id.get_manage_license:

                it.setClass(context, PickImgActivity.class);
                startActivityForResult(it, 101);

                break;
            case R.id.get_no:


                break;

        }
    }
}
