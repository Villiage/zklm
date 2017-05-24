package com.fxlc.zklm.test;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.util.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestActivity extends AppCompatActivity {
    private final static String TAG = "TestActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_test);
        listView = (ListView) findViewById(R.id.list);

        final View rootview = findViewById(R.id.root);

        List<Contact> list = readContact();
        for (Contact contact : list) {

            Log.d(TAG, contact.getFirst() + contact.getName());
        }
        listView.setAdapter(new ContactAdapter(list));
    }

    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    CharacterParser characterParser = new CharacterParser();

    public void decode(String str) {

        char c = characterParser.getSelling(str).charAt(0);
        Log.d(TAG, c + "");
    }


    Set<Character> set = new HashSet<>();

    private List<Contact> readContact() {
        List<Contact> cList = new ArrayList<>();
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
                Contact c = new Contact(name, phoneNum);
                c.setFirst(characterParser.convert(name.substring(0, 1)).toUpperCase().charAt(0));
                cList.add(c);

            }

        }
        Collections.sort(cList, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {


                return c1.getFirst().compareTo(c2.getFirst());
            }
        });
        return cList;
    }

    class ContactAdapter extends BaseAdapter {
        private List<Contact> dataList;

        public ContactAdapter(List<Contact> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Contact getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            TextView txt1, txt2, charTxt;
            if (view == null) {
//                int id = android.R.layout.simple_list_item_2;
                int id = R.layout.item_contact;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
            }
            Contact con = dataList.get(i);
            charTxt = (TextView) view.findViewById(R.id.firstchar);
            txt1 = (TextView) view.findViewById(R.id.text1);
            txt2 = (TextView) view.findViewById(R.id.text2);
            txt1.setText(con.getName());
            txt2.setText(con.getNum());
            charTxt.setText(con.getFirst() + "");
            if (i > 0 && con.getFirst() == dataList.get(i - 1).getFirst()) {
               charTxt.setVisibility(View.GONE);
            }else charTxt.setVisibility(View.VISIBLE);

            return view;
        }
    }
}
