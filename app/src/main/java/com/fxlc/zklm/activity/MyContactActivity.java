package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxlc.zklm.ListActiviity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.bean.MyContact;
import com.fxlc.zklm.net.HttpCallback;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.ContactService;
import com.fxlc.zklm.util.DialogUtil;
import com.fxlc.zklm.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MyContactActivity extends ListActiviity implements View.OnClickListener {
    List<Contact> contactList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_contact);
        super.onCreate(savedInstanceState);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                it.setClass(ctx,ContactInfoActivity.class);
                it.putExtra("contact",contactList.get(i));
                startActivity(it);
            }
        });

        FrameLayout topbar = (FrameLayout) findViewById(R.id.topbar);
        ImageView addView = new ImageView(this);
        addView.setImageResource(R.drawable.contact_add);
        addView.setId(R.id.add);
        addView.setOnClickListener(this);
        addView.setScaleType(ImageView.ScaleType.CENTER);
        int pad = DisplayUtil.dp2px(this, 10);
        addView.setPadding(pad, pad, pad, pad);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        topbar.addView(addView, params);
        dialog = DialogUtil.createWBDialog(ctx, new String[]{"从通讯录添加", "新建联系人"}, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("我的联系人");
        loadData();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.add:

                dialog.show();
                break;
            case R.id.dialog_item1:
                it.setClass(ctx,ContactActivity.class);
                startActivity(it);
                dialog.dismiss();
                break;
            case R.id.dialog_item2:
                dialog.dismiss();
                it.setClass(ctx,AddContactActivity.class);
                startActivity(it);
                break;
        }
    }
    @Override
    public void loadData() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();

        ContactService service = retrofit.create(ContactService.class);

        Call<HttpResult<MyContact>> call = service.getContact();

        call.enqueue(new HttpCallback<MyContact>() {
            @Override
            public void onSuccess(MyContact myContact) {
                proDialog.dismiss();
                contactList = myContact.getContact();
                if (contactList != null && !contactList.isEmpty()){
                    showDataView();
                    adapter = new ContactAdapter();
                    listView.setAdapter(adapter);
                }else {
                    showEmptyView();
                }

            }
        });


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
            check.setVisibility(View.GONE);
            txt1.setText(contactList.get(i).getName());
            txt2.setText(contactList.get(i).getPhone());

            return view;
        }

    }


}
