package com.fxlc.zklm.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.service.ContactService;
import com.fxlc.zklm.util.DialogUtil;
import com.fxlc.zklm.util.DisplayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyContactActivity extends BaseActivity implements View.OnClickListener {
    List<Contact> contactList = new ArrayList<>();
    ListView listView;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);
        listView = (ListView) findViewById(R.id.list);
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
        dialog = DialogUtil.createWBDialog(ctx, new String[]{"通讯录", "新建联系人"}, this);
        getContact();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("我的联系人");
        getContact();
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
                break;
            case R.id.dialog_item2:
                it.setClass(ctx,AddContactActivity.class);
                startActivity(it);

                break;
        }
    }

    private void getContact() {
        proDialog.show();
        Retrofit retrofit = MyApplication.getRetrofit();

        ContactService service = retrofit.create(ContactService.class);
        Call<ResponseBody> call = service.getContact();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 proDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String resultStr = response.body().string();

                        JSONObject json = new JSONObject(resultStr);
                        String conJson = json.getJSONObject("body").getString("contact");
                        contactList = new Gson().fromJson(conJson, new TypeToken<List<Contact>>(){}.getType());
                        adapter = new ContactAdapter();
                        listView.setAdapter(adapter);

                    }

                    } catch(IOException e){
                        e.printStackTrace();
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
