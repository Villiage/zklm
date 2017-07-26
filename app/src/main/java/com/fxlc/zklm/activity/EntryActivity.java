package com.fxlc.zklm.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.fxlc.zklm.util.FileUtil;

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
                contact.setPhone("1371826944");
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
                it.setClass(context, LicenseAuditActivity.class);
                startActivity(it);

            }
        });
    }

    private void permission(){

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED) {
//            Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(takephoto, 200);
//        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100){

              if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                  Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(takephoto, 200);
              }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                  Toast.makeText(this," permission denied,need granted in the setting",Toast.LENGTH_SHORT).show();
              }

        }

    }
}
