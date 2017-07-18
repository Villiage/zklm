package com.fxlc.zklm.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.ContactService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ContactActivity extends BaseActivity implements View.OnClickListener {
    List<Contact> contactList = new ArrayList<>();
    ListView listView;

    List<Contact> checkList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = (ListView) findViewById(R.id.list);
        findViewById(R.id.checkall).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("手机通讯录");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.check:
                Contact contact = (Contact) view.getTag();

                view.setSelected(contact.check = !contact.check);


                break;
            case R.id.checkall:

                for (Contact cont : contactList) {
                     cont.check = !view.isSelected();
                }
                adapter.notifyDataSetChanged();
                view.setSelected(!view.isSelected());
                break;

            case R.id.add:

                for (Contact con : contactList) {
                     if (con.check){
                         checkList.add(con);
                     }
                }

                submit(checkList);


                break;
        }
    }
    private void submit(List<Contact> checkList){

        Retrofit  retrofit = MyApplication.getRetrofit();
        ContactService service = retrofit.create(ContactService.class);
        HashMap<String,String> map = new HashMap();

        String contactGson = new Gson().toJson(checkList);
        Log.d("param",contactGson);
        map.put("contact",contactGson);

        Call<HttpResult> call = service.addContact2(map);
        call.enqueue(new SimpleCallback() {


            @Override
            public void onSuccess(HttpResult result) {
                 toast(result.getMsg());
            }
        });

    }
    private void readContact() {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {

            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            if (phone.moveToFirst()) {
                String phoneNum = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.d("phone:", name + ":" + phoneNum);
                contactList.add(new Contact(name, phoneNum));
            }

        }
        adapter = new ContactAdapter();
        listView.setAdapter(adapter);

    }

    private ContactAdapter adapter;


    class ContactAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public Contact getItem(int i) {
            return contactList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView txt1, txt2;
            View check;
            Contact contact = contactList.get(i);
            if (view == null) {
//                int id = android.R.layout.simple_list_item_2;
                int id = R.layout.list_item_contact;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
            }
            txt1 = (TextView) view.findViewById(R.id.name);
            txt2 = (TextView) view.findViewById(R.id.mobile);
            check = view.findViewById(R.id.check);
            check.setSelected(contact.check);
            txt1.setText(contactList.get(i).getName());
            txt2.setText(contactList.get(i).getPhone());
            check.setOnClickListener(ContactActivity.this);
            check.setTag(contact);
            return view;
        }

    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 102);
        } else {
            readContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContact();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
