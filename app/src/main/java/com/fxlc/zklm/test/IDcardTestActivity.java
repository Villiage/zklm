package com.fxlc.zklm.test;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.IDcard;
import com.fxlc.zklm.util.BitmapUtil;
import com.fxlc.zklm.util.DialogUtil;
import com.fxlc.zklm.util.FileUtil;
import com.fxlc.zklm.util.TransformationUtils;
import com.fxlc.zklm.util.UriUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IDcardTestActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "IDcardTestActivity";
    public static int ALBUM_CODE = 100;
    public static int CAPTURE_CODE = 101;
    public static String captureTempFile = "capture_temp.jpg";
    public File captureFile;
    private String photoPath;
    private ImageView idcardImg;
    private TextView idNoTxt, realNameTxt;
    private View verify;
    private ProgressDialog dialog;
    private boolean verifySuccess;
    private Bitmap bitmap;
    private Bitmap.CompressFormat format;
    public static final int IDcard_REQURIE_WIDTH = 800;
    public static final int IDcard_REQURIE_HEIGHT = 800;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String dataValue = (String) msg.obj;
                JSONObject outputObj = null;
                try {
                    outputObj = new JSONObject(dataValue);
                    if (outputObj.getBoolean("success")) {

                        idcard1.setName(outputObj.getString("name"));
                        idcard1.setNum(outputObj.getString("num"));
                        idNoTxt.setText(idcard1.getNum());
                        realNameTxt.setText(idcard1.getName());
//                        checkName();
                    } else {

                        Log.d("Card","照片识别失败，请重新上传" + "\n");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else if (msg.what == 1) {
                String result = (String) msg.obj;
                try {
                    JSONObject obj = new JSONObject(result);
                    int code = obj.getInt("code");
                    if (code == 1) {

                        idcard2.setAddress(obj.getString("address"));
                        idcard2.setSex(obj.getString("sex"));
                        idcard2.setBirth(obj.getString("birthday"));

                        verifySuccess = true;
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
        setContentView(R.layout.activity_idcard_test);
        idcardImg = (ImageView) findViewById(R.id.img_face);
        idcardImg.setOnClickListener(this);

        verify = findViewById(R.id.verify);
        verify.setOnClickListener(this);

        idNoTxt = (TextView) findViewById(R.id.id_no);
        realNameTxt = (TextView) findViewById(R.id.realname);


        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

    }


    OkHttpClient client = new OkHttpClient();

    Dialog choiceDialog = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_face:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    choiceDialog = DialogUtil.createDialog(IDcardTestActivity.this, new String[]{"相册", "拍照"}, this);
                    choiceDialog.show();
                }

                break;

            case R.id.verify:
                readCard();
                break;
            case R.id.dialog_item1:
                choiceDialog.dismiss();
                Intent album = new Intent(Intent.ACTION_PICK);
                album.setType("image/*");
                startActivityForResult(album, ALBUM_CODE);
                break;
            case R.id.dialog_item2:
                choiceDialog.dismiss();
                Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                captureFile = FileUtil.getSaveFile(IDcardTestActivity.this, captureTempFile);
                Uri uri = Uri.fromFile(captureFile);
                takephoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takephoto, CAPTURE_CODE);
                break;
            case R.id.dialog_close:
                choiceDialog.dismiss();
                break;

        }
    }

    IDcard idcard1 = new IDcard();
    IDcard idcard2 = new IDcard();

    public void readCard() {
        dialog.setMessage("正在读取照片信息,请稍侯...");
        dialog.show();

        final String host = "http://dm-51.data.aliyun.com";
        final String path = "/rest/160601/ocr/ocr_idcard.json";
        final String appcode = "60091d0fb1a648459827272c0abf7909";
        byte[] content = BitmapUtil.bitmapToByte(bitmap, format);
        String imgBase64 = android.util.Base64.encodeToString(content, android.util.Base64.DEFAULT);

        final JSONObject requestObj = new JSONObject();
        try {
            JSONObject configObj = new JSONObject();
            JSONObject obj = new JSONObject();
            JSONArray inputArray = new JSONArray();
            configObj.put("side", "face");
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
                Toast.makeText(IDcardTestActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                if (response.code() == 200) {
                    try {
                        String result = response.body().string();
                        Log.d(TAG, result);
                        JSONObject obj = new JSONObject(result);
                        JSONArray outputs = obj.getJSONArray("outputs");
                        String outputValue = outputs.getJSONObject(0).optJSONObject("outputValue").getString("dataValue");
                        Log.d(TAG, outputValue);
                        Message msg = Message.obtain();
                        msg.what = 0;
                        msg.obj = outputValue;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(IDcardTestActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void checkName() {
        dialog.setMessage("正在验证姓名和身份证号,请稍候...");
        dialog.show();
        String url = "http://aliyun.id98.cn/idcard";
        String appcode = "60091d0fb1a648459827272c0abf7909";
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("name", realNameTxt.getText().toString());
        querys.put("cardno", idNoTxt.getText().toString());


        final Request request = new Request.Builder()
                .url(url + "?" + buildParamString(querys))
                .header("Authorization", "APPCODE " + appcode)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();

                if (response.code() == 200) {
                    String result = response.body().string();
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } else {
                    Toast.makeText(IDcardTestActivity.this, "验证失败", Toast.LENGTH_SHORT).show();

                }
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
        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent album = new Intent(Intent.ACTION_PICK);
                album.setType("image/*");
                startActivityForResult(album, ALBUM_CODE);
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == ALBUM_CODE) {

                Uri uri = data.getData();
                photoPath = UriUtil.getRealFilePath(this, uri);

                getBitmap();

            }
            if (requestCode == CAPTURE_CODE) {
                photoPath = captureFile.getPath();
                getBitmap();

            }


        }

    }

    public void getBitmap() {
        format = BitmapUtil.getMime(photoPath);
        bitmap = BitmapUtil.getSmallBitmap(photoPath, IDcard_REQURIE_WIDTH, IDcard_REQURIE_HEIGHT);

        try {
            ExifInterface exifInterface = new ExifInterface(photoPath);
            int exifOrientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree = TransformationUtils.getExifOrientationDegrees(exifOrientation);
            Log.d(TAG, "angle:" + degree);
            bitmap = TransformationUtils.rotateImage(bitmap, degree);
            idcardImg.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildParamString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        if (null != params && params.size() > 0) {
            boolean isFirst = true;
            for (String key : params.keySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    result.append("&");
                }
                try {
                    result.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }

        return result.toString();
    }


}
