package com.fxlc.zklm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.bean.MediaStoreData;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.MyThrowable;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.PayService;
import com.fxlc.zklm.net.service.UserService;
import com.fxlc.zklm.service.UploadService;
import com.fxlc.zklm.util.DisplayUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PayActivity extends BaseActivity implements View.OnClickListener {

    ListView listView;
    MAdapter adapter;
    ImageView addPic;
    TextView fromName, toName, fromAcount, toAcount;
    TextView amountTxt, remark;
    View payV;
    Contact contact;
    String  contactRealName;
    User user;
    Dialog payDialog;
    StringBuffer sb = new StringBuffer();
    TextView[] pswTxtArr = new TextView[6];
    Retrofit retrofit;
    PayService service;
    AlertDialog pwdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        contactRealName = getIntent().getStringExtra("contact_name");
        user = MyApplication.getUser();
        listView = (ListView) findViewById(R.id.list);
        addPic = (ImageView) findViewById(R.id.addpic);
        addPic.setOnClickListener(this);
        fromName = (TextView) findViewById(R.id.fromName);
        fromAcount = (TextView) findViewById(R.id.fromAcount);
        toName = (TextView) findViewById(R.id.toName);
        toAcount = (TextView) findViewById(R.id.toAcount);
        amountTxt = (TextView) findViewById(R.id.payAmount);
        remark = (TextView) findViewById(R.id.remark);
        payV = findViewById(R.id.pay);
        amountTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString().trim();
                if (s .length() > 0){
                    float a = Float.valueOf(s);
                    if (a > 0) {
                        payV.setEnabled(true);
                    } else {
                        payV.setEnabled(false);
                    }
                }else{
                    payV.setEnabled(false);
                }



            }
        });
        findViewById(R.id.pay).setOnClickListener(this);
        adapter = new MAdapter();
        listView.setAdapter(adapter);
        setValue();
        initDialog();
        retrofit = MyApplication.getRetrofit();
        service = retrofit.create(PayService.class);
        pwdDialog = new AlertDialog.Builder(this).create();

    }

    @Override
    protected void onResume() {
        super.onResume();
        title("支付");
    }

    private void verifyPwd() {


        String pwd = sb.toString();
        Call call = service.verifypwd(pwd);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {
                if (result.isSuccess()) {
                    pay();
                }
            }
        });

        sb = new StringBuffer();
        for (TextView textView : pswTxtArr) {
            textView.getEditableText().clear();
        }
    }
    private void isPayPass(){

         PayService service = retrofit.create(PayService.class);

         Call<HttpResult>  call = service.isPaypass();
         call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

                     payDialog.show();

            }

             @Override
             public void onFailure(Call<HttpResult> call, Throwable throwable) {
                 super.onFailure(call, throwable);
                 if (throwable instanceof MyThrowable){
                     pwdDialog.setMessage("请先设置支付密码");
                     pwdDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();

                         }
                     });
                     pwdDialog.setButton(AlertDialog.BUTTON_POSITIVE, "去设置", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                             it.setClass(ctx,ChangePayPwdActivity.class);
                             it.putExtra("title","设置支付密码");
                             startActivity(it);
                         }
                     });
                     pwdDialog.setCanceledOnTouchOutside(false);
                     pwdDialog.show();
                 }
             }
         });

    }

    private void pay() {
        proDialog.show();
        String amount = amountTxt.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("incomeId", contact.getId());
        map.put("paymentMoney", amount);
        map.put("remarks", remark.getText().toString());
        Call<HttpResult> call = service.pay(map);
        call.enqueue(new SimpleCallback() {

            @Override
            public void onSuccess(HttpResult result) {
                  proDialog.dismiss();

                  if (result.isSuccess()){
                      String json = new Gson().toJson(result);

                      try {
                          String payId = new JSONObject(json).getJSONObject("body").getString("payId");
                          UploadService.actionAddBillImg(ctx,payId,adapter.getDataList());
                          it.setClass(ctx,PayResultActivity.class);
                          startActivity(it);
                          finish();
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }


                  }
            }
        });
        sb = new StringBuffer();
        for (TextView textView : pswTxtArr) {
            textView.getEditableText().clear();
        }
    }


    private void initDialog() {
        final String[] nums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "*"};
        payDialog = new Dialog(ctx, R.style.dialog_alert);
        payDialog.setContentView(R.layout.dialog_pay);

        ViewGroup group = (ViewGroup) payDialog.findViewById(R.id.pwdGroup);
        for (int i = 0; i < pswTxtArr.length; i++) {
            pswTxtArr[i] = (TextView) group.getChildAt(i * 2);
        }
        GridView grid = (GridView) payDialog.findViewById(R.id.number);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = nums[i];
                if (i == nums.length - 1) {
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                        pswTxtArr[sb.length()].getEditableText().clear();
                    }
                } else if (i != 9) {
                    sb.append(s);
                    pswTxtArr[sb.length() - 1].setText(s);
                    if (sb.length() == 6) {
                        payDialog.dismiss();
                        verifyPwd();
                    }
                }


            }
        });
        grid.setAdapter(new NumberAdapter(nums));
        Window win = payDialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    class NumberAdapter extends BaseAdapter {
        String[] nums;
        int pading;

        public NumberAdapter(String[] nums) {
            this.nums = nums;
            pading = DisplayUtil.dp2px(ctx, 15);
        }

        @Override
        public int getCount() {
            return nums.length;
        }

        @Override
        public String getItem(int i) {
            return nums[i];
        }

        @Override
        public long getItemId(int i) {

            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView text = new TextView(viewGroup.getContext());
            text.setGravity(Gravity.CENTER);

            text.setPadding(0, pading, 0, pading);
            text.setTextColor(getResources().getColor(R.color.text_primary));
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            text.setText(nums[i]);

            if (i == nums.length - 1 || i == 9) {

            } else {
                text.setBackgroundResource(R.drawable.bg_click);
            }


            return text;
        }
    }

    private void setValue() {
        if (contact != null) {
            toName.setText(contactRealName);
            toAcount.setText(contact.getPhone());
            fromName.setText(user.getName());
            fromAcount.setText(user.getMobile());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addpic:

                it.setClass(ctx, PickImgActivity.class);
                startActivityForResult(it, 102);
                break;
            case R.id.pay:

//                payDialog.show();
//                UploadService.actionAddBillImg(ctx,"8eb78b1563ba4820a4b0f6e65c20597d",adapter.getDataList());
                isPayPass();
                break;

        }
    }

    class MAdapter extends BaseAdapter {
        private ArrayList<MediaStoreData> dataList;


        public ArrayList<MediaStoreData> getDataList() {
            return dataList;
        }

        public void addData(List<MediaStoreData> dataList) {
            if (this.dataList == null) this.dataList = new ArrayList<>();
            this.dataList.addAll(0, dataList);
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public MediaStoreData getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MediaStoreData data = dataList.get(i);
            ImageView img;
            if (view == null) {
                img = new ImageView(viewGroup.getContext());
            } else img = (ImageView) view;
            img.setBackgroundResource(R.drawable.pay_bg);
            Glide.with(ctx).load(data.uri).fitCenter().into(img);

            return img;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 102) {
                ArrayList<MediaStoreData> selList = data.getParcelableArrayListExtra("imgs");
                adapter.addData(selList);

            }


        }

    }
}
