package com.fxlc.zklm.test;

import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cyd on 2017/6/12.
 */

public class BackupCode {

//    public void checkName() {
//        dialog.setMessage("正在验证姓名和身份证号,请稍候...");
//        dialog.show();
//        String url = "http://aliyun.id98.cn/idcard";
//        String appcode = "60091d0fb1a648459827272c0abf7909";
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("name", realNameTxt.getText().toString());
//        querys.put("cardno", idNoTxt.getText().toString());
//
//
//        final Request request = new Request.Builder()
//                .url(url + "?" + buildParamString(querys))
//                .header("Authorization", "APPCODE " + appcode)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                dialog.dismiss();
//
//                if (response.code() == 200) {
//                    String result = response.body().string();
//                    Message msg = Message.obtain();
//                    msg.what = 1;
//                    msg.obj = result;
//                    handler.sendMessage(msg);
//                } else {
//                    Toast.makeText(IDcardTestActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//
//
//    }
//   private String buildParamString(Map<String, String> params) {
//    StringBuilder result = new StringBuilder();
//    if (null != params && params.size() > 0) {
//        boolean isFirst = true;
//        for (String key : params.keySet()) {
//            if (isFirst) {
//                isFirst = false;
//            } else {
//                result.append("&");
//            }
//            try {
//                result.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));
//            } catch (UnsupportedEncodingException ex) {
//                throw new RuntimeException(ex);
//            }
//
//        }
//    }
//
//    return result.toString();
//}

}
