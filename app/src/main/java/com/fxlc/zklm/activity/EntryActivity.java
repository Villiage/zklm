package com.fxlc.zklm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.test.IDcardTestActivity;
import com.fxlc.zklm.test.PlaceTestActivity;
import com.fxlc.zklm.test.ScanQRActivity;
import com.fxlc.zklm.test.SqlActivity;
import com.fxlc.zklm.test.TestActivity;

public class EntryActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_entry);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, IDcardAuditActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                Contact contact = new Contact();
                contact.setName("常亚东");
                contact.setPhone("13256489546");
                it.setClass(context, ContactInfoActivity.class);
                it.putExtra("contact",contact);
                startActivity(it);

            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, HomeActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, LoginActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, AddCarActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, PayActivity.class);
                startActivity(it);

            }
        });

        findViewById(R.id.qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, ScanQRActivity.class);
                startActivity(it);

            }
        });


        findViewById(R.id.mycar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, MycarActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, ShareActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, PickImgActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, SettingActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.my).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, MyActivity.class);
                startActivity(it);

            }
        });
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(context, GuideActivity.class);
                startActivity(it);

            }
        });
    }
}
