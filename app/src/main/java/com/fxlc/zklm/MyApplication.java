package com.fxlc.zklm;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fxlc.zklm.bean.IDcard;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.db.CarDao;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cyd on 2017/4/20.
 */

public class MyApplication extends Application {


    private static MyApplication instance;
    private static User user;
    private static Retrofit retrofit;
    private static SharedPreferences sp;
    private static IDcard iDcard;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        sp = getSharedPreferences("app", Context.MODE_PRIVATE);
        initUser();
//      initIDcard();
        initRetrofit();
        initCarsData();

    }

    public static MyApplication getInstance() {


        return instance;
    }

    public static void exit() {
        user = null;
        sp.edit().remove("user").commit();
    }

    public static Retrofit getRetrofit() {


        return retrofit;
    }


    private void initRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d("request", request.url().toString());
                HttpUrl.Builder urlBuilder = request.url().newBuilder();
                if (user != null) {
                    urlBuilder.addQueryParameter("userId", user.getId());
                    urlBuilder.addQueryParameter("token", user.getToken());
                }
                request = request.newBuilder().url(urlBuilder.build()).build();
                okhttp3.Response response = chain.proceed(request);

                return response;
            }
        }).build();

        retrofit = new Retrofit.Builder().baseUrl(Constant.Host)
                //     .addConverterFactory( StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    public static SharedPreferences getSP() {

        return sp;
    }

    private void initUser() {
        String userStr = sp.getString("user", "");
        if (userStr != null && !userStr.equals(""))
            user = new Gson().fromJson(userStr, User.class);


    }

    public static User getUser() {


        return user;
    }


//    private void initIDcard() {
//        String s = sp.getString("idcard" + user.getId(), "");
//        iDcard = new Gson().fromJson(s, IDcard.class);
//    }


    public static void setUser(User u) {
        user = u;
    }

    public void initCarsData() {
           CarDao dao = new CarDao(getApplicationContext());
        if (!dao.hasData()) {

            try {
                InputStream is = getAssets().open("car.xls");
                dao.readExcel(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
