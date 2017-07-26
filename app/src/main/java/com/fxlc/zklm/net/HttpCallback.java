package com.fxlc.zklm.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.activity.LoginActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cyd on 2017/6/14.
 */

public abstract class HttpCallback<T> implements Callback<HttpResult<T>> {
    private Context context;
//    public HttpCallback(Context context) {
//        this.context = context;
//    }

    @Override
    public void onResponse(Call<HttpResult<T>> call, Response<HttpResult<T>> response) {

        HttpResult<T> result = response.body();

        if (result != null){
            if (result.isSuccess()){
                onSuccess(result.getBody());
                Log.d("response", new Gson().toJson(result));
            }
            else {
                onFailure(call,new MyThrowable());
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
               if (result.getErrorCode().equals("01")){
                   MyApplication.exit();
               }
            }

        }


    }

    @Override
    public void onFailure(Call call, Throwable t) {

        if (t instanceof MyThrowable){

        }else {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
        }

    }

    public abstract void onSuccess(T t);


}
