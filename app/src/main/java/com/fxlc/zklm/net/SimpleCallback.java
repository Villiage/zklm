package com.fxlc.zklm.net;

import android.util.Log;
import android.widget.Toast;

import com.fxlc.zklm.MyApplication;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cyd on 2017/6/21.
 */

public abstract class SimpleCallback implements Callback<HttpResult> {
    @Override
    public void onResponse(Call<HttpResult> call, Response<HttpResult> response) {


        HttpResult result = response.body();

        if (result != null) {
            Log.d("response", new Gson().toJson(result));
            onSuccess(result);
            if (!result.isSuccess()) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFailure(Call<HttpResult> call, Throwable throwable) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
    }

    public abstract void onSuccess(HttpResult result);
}