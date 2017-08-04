package com.fxlc.zklm.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxlc.zklm.BaseActivity;
import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.IDcard;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.service.UserService;
import com.fxlc.zklm.util.BitmapUtil;
import com.fxlc.zklm.util.DialogUtil;
import com.fxlc.zklm.util.FileUtil;
import com.fxlc.zklm.util.UriUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class IDcardAuditActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "IDcardTestActivity";
    public static int ALBUM_CODE = 100;
    public static int CAPTURE_CODE = 101;
    public static String faceTempFile = "face_temp.jpg";
    public static String backTempFile = "back_temp.jpg";
    public File captureFile;
    private String facePath, backPath;
    private byte[] faceContent, backContent;
    private boolean faceSuccess, backSuccess;
    private ImageView faceImg, backImg;
    private int type = 1;
    IDcard idcard = new IDcard();
    private TextView idNoTxt, realNameTxt, issueTxt;
    private View verify;
    private Retrofit retrofit;
    private User user;
    private ProgressDialog dialog;

    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                toast("照片识别失败，请重新上传");
                return;
            }
            if (type == 1) {
                String dataValue = (String) msg.obj;
                JSONObject outputObj = null;
                try {
                    outputObj = new JSONObject(dataValue);
                    if (outputObj.getBoolean("success")) {
                        faceSuccess = true;
                        idcard.setName(outputObj.getString("name"));
                        idcard.setNum(outputObj.getString("num"));

                        idNoTxt.setText(idcard.getNum());
                        realNameTxt.setText(idcard.getName());
                    } else {
                        toast("照片识别失败，请重新上传");
                        Log.d("Card", "照片识别失败，请重新上传" + "\n");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (type == 2) {
                String dataValue = (String) msg.obj;
                JSONObject outputObj = null;
                try {
                    outputObj = new JSONObject(dataValue);
                    if (outputObj.getBoolean("success")) {
                        backSuccess = true;
                        idcard.setIssue(outputObj.getString("issue"));
                        issueTxt.setText(idcard.getIssue());

                    } else {
                        toast("照片识别失败，请重新上传");
                        Log.d("Card", "照片识别失败，请重新上传" + "\n");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_idcard_test);

        faceImg = (ImageView) findViewById(R.id.faceImg);
        backImg = (ImageView) findViewById(R.id.backImg);
        findViewById(R.id.img_face).setOnClickListener(this);
        findViewById(R.id.img_back).setOnClickListener(this);

        verify = findViewById(R.id.verify);
        verify.setOnClickListener(this);

        idNoTxt = (TextView) findViewById(R.id.id_no);
        realNameTxt = (TextView) findViewById(R.id.realname);
        issueTxt = (TextView) findViewById(R.id.issue);

        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        choiceDialog = DialogUtil.createDialog(IDcardAuditActivity.this, new String[]{"相册", "拍照"}, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        title("个人认证");
    }

    OkHttpClient client = new OkHttpClient();

    Dialog choiceDialog = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_face:
                type = 1;
                choiceDialog.show();
                break;
            case R.id.img_back:
                type = 2;
                choiceDialog.show();
                break;

            case R.id.verify:
                if (faceSuccess && backSuccess)
                    submit();
                else
                    Toast.makeText(context, "照片信息不完整或不清晰，请重新选取", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dialog_item1:
                choiceDialog.dismiss();
                Intent album = new Intent(Intent.ACTION_PICK);
                album.setType("image/*");
                startActivityForResult(album, ALBUM_CODE);
                break;
            case R.id.dialog_item2:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                choiceDialog.dismiss();
                break;
            case R.id.dialog_close:
                choiceDialog.dismiss();
                break;

        }
    }

    private void photo() {
        faceTempFile += System.currentTimeMillis();
        backTempFile += System.currentTimeMillis();
        Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureFile = FileUtil.getSaveFile(ctx, type == 1 ? faceTempFile : backTempFile);
        Uri uri = Uri.fromFile(captureFile);
        takephoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(takephoto, CAPTURE_CODE);


    }

    public void readCard(String photoPath) {
        dialog.setMessage("正在验证照片,请稍侯...");
        dialog.show();

        final String host = "http://dm-51.data.aliyun.com";
        final String path = "/rest/160601/ocr/ocr_idcard.json";
        final String appcode = "60091d0fb1a648459827272c0abf7909";

        byte[] content = BitmapUtil.cpPicToByte(photoPath, 200);
        if (type == 1) faceContent = content;
        else if (type == 2) backContent = content;
        String imgBase64 = android.util.Base64.encodeToString(content, android.util.Base64.DEFAULT);

        final JSONObject requestObj = new JSONObject();
        try {
            JSONObject configObj = new JSONObject();
            JSONObject obj = new JSONObject();
            JSONArray inputArray = new JSONArray();
            if (type == 1)
                configObj.put("side", "face");
            else
                configObj.put("side", "back");
            obj.put("image", getParam(50, imgBase64));
            obj.put("configure", getParam(50, configObj.toString()));
            inputArray.put(obj);
            requestObj.put("inputs", inputArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String body = requestObj.toString();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, body);
        final Request request = new Request.Builder()
                .url(host + path)
                .header("Authorization", "APPCODE " + appcode)
                .header("Content-Type", "application/json; charset=UTF-8")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Toast.makeText(ctx, "验证失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                Log.d(TAG, "response:" + response.code());

                if (response.code() == 200) {
                    try {
                        String result = response.body().string();

                        JSONObject obj = new JSONObject(result);
                        JSONArray outputs = obj.getJSONArray("outputs");
                        String outputValue = outputs.getJSONObject(0).optJSONObject("outputValue").getString("dataValue");

                        Message msg = Message.obtain();
                        msg.what = 0;
                        msg.obj = outputValue;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(-1);
                    }

                } else {
                    handler.sendEmptyMessage(-1);

                }
            }
        });


    }

    public void submit() {
        proDialog.show();
        user = MyApplication.getUser();
        retrofit = MyApplication.getRetrofit();
        UserService service = retrofit.create(UserService.class);
        HashMap map = new HashMap();

        map.put("id", user.getId());
        map.put("name", idcard.getName());
        map.put("cardnumber", idcard.getNum());
        map.put("cardorgan", idcard.getIssue());
        map.put("pstatus", 2);
        File faceFile = new File(facePath);
        File backFile = new File(backPath);

        RequestBody faceBody =
                RequestBody.create(MediaType.parse("application/otcet-stream"), faceContent);
        MultipartBody.Part facePart =
                MultipartBody.Part.createFormData("pcardpositive", faceFile.getName(), faceBody);
        RequestBody backBody =
                RequestBody.create(MediaType.parse("application/otcet-stream"), backContent);
        MultipartBody.Part backPart =
                MultipartBody.Part.createFormData("pcardreverse", backFile.getName(), backBody);

        retrofit2.Call<HttpResult> call = service.saveIDcard2(facePart, backPart, map);
        call.enqueue(new retrofit2.Callback<HttpResult>() {
            @Override
            public void onResponse(retrofit2.Call<HttpResult> call, retrofit2.Response<HttpResult> response) {
                proDialog.dismiss();


                if (response.body().isSuccess()) {
                    user.setName(idcard.getName());
                    save("user", new Gson().toJson(user));
                    MyApplication.setUser(user);
                    finish();
                }


            }

            @Override
            public void onFailure(retrofit2.Call<HttpResult> call, Throwable t) {
                toast(netErrorMsg);
                proDialog.dismiss();
            }
        });

    }


    /*
       * 获取参数的json对象
       */

    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                photo();
            } else {
                // Permission Denied
                Toast.makeText(this, "请在 设置->权限 中授权拍照", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            String photoPath = null;
            if (requestCode == ALBUM_CODE) {
                Uri uri = data.getData();
                photoPath = UriUtil.getRealFilePath(this, uri);

            }
            if (requestCode == CAPTURE_CODE) {
                photoPath = captureFile.getPath();
            }
            Log.d(TAG, photoPath);
            if (type == 1) {
                Glide.with(context).load(facePath = photoPath).fitCenter().into(faceImg);

            } else if (type == 2) {
                Glide.with(context).load(backPath = photoPath).fitCenter().into(backImg);
            }
            readCard(photoPath);
        }

    }


}
