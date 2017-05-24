package com.fxlc.zklm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fxlc.zklm.R;
import com.fxlc.zklm.test.ContactActivity;
import com.fxlc.zklm.util.DialogUtil;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         String [] arr = new String[]{"从手机联系人添加","手动添加"};
         findViewById(R.id.add_contact).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 DialogUtil.createDialog(HomeActivity.this, new String[]{"",""}, new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                          switch ((Integer) view.getTag()){
                              case 0:
                                  Intent it = new Intent();
                                  it.setClass(HomeActivity.this, ContactActivity.class);
                                  startActivity(it);
                                  break;
                              case 1:

                                  break;

                          }

                     }
                 }) ;
             }
         });


    }
}
