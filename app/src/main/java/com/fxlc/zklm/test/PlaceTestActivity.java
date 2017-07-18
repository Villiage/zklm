package com.fxlc.zklm.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.bean.WeatherCity;
import com.fxlc.zklm.net.service.BDPlaceService;
import com.fxlc.zklm.net.service.WeatherCityService;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceTestActivity extends AppCompatActivity {

    GridView gridView;

    private String placeStr = "", pstr = "", cstr = "", dstr = "";
    private TextView placeTxt;
    private int level = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dbtest);
        placeTxt = (TextView) findViewById(R.id.place);
        gridView = (GridView) findViewById(R.id.grid);
        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cityList = cityDao.queryBuilder().where(CityDao.Properties.Parentid.eq("0")).list();
//                Log.d("city", cityList.size() + "");
//                level = 0;
//                gridView.setVisibility(View.VISIBLE);
//                cityAdapter = new CityAdapter();
//                gridView.setAdapter(cityAdapter);
//                getAllCity();
                  requestTest();
//                 clientCall();
            }
        });

    }



    public void getAllCity() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url  = request.url().newBuilder().addQueryParameter("","").build();

                request = request.newBuilder().url(url).build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        }).build();
        String URL_WeatherCity = "http://jisutqybmf.market.alicloudapi.com/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_WeatherCity)
                .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory( StringConverterFactory.create())
                .client(client)
                .build();

        WeatherCityService service = retrofit.create(WeatherCityService.class);
        final String auth = "APPCODE " + "60091d0fb1a648459827272c0abf7909";

        Call<WeatherCity> citys = service.listCity(auth);

        citys.enqueue(new Callback<WeatherCity>() {
            @Override
            public void onResponse(Call<WeatherCity> call, Response<WeatherCity> response) {
                WeatherCity weatherCity = response.body();
                Log.d("weather", weatherCity.getMsg() + weatherCity.getResult().size());
//              List<City> cityList = weatherCity.getResult();


            }

            @Override
            public void onFailure(Call<WeatherCity> call, Throwable t) {

            }
        });

    }
    File file = new File("/storage/emulated/0/MagazineUnlock/magazine-unlock-04-2.3.651-_0379560f1efe4d3e8435c938591e2461.jpg");
    public void requestTest() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url  = request.url().newBuilder()
//                        .addQueryParameter("name","zhangshan")
//                        .addQueryParameter("age","20")
                        .build();

                request = request.newBuilder().url(url).build();
                okhttp3.Response response = chain.proceed(request);
                Log.d("response",response.body().string());
                return response;
            }
        }).build();
        String phpurl = "http://192.168.1.106/httptest/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(phpurl)
                .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory( StringConverterFactory.create())
                .client(client)
                .build();

        PhpService service = retrofit.create(PhpService.class);

        User user1 = new User();
        user1.setId("1");
        user1.setName("lisi");
        User user2 = new User();
        user2.setId("2");
        user2.setName("wangwu");
        List<User> users = new ArrayList();

        users.add(user1);
        users.add(user2);
        String json = new Gson().toJson(users);
        RequestBody     jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);


        String type = "application/otcet-stream";
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img",file.getName(), RequestBody.create(MediaType.parse(type),file))
                .build();

//        MultipartBody.Part part =  MultipartBody.Part.createFormData("photo",file.getName(),requestBody);
//         RequestBody requestBody = MultipartBody.create(MediaType.parse("multipart/form-data"),file);


        Call<ResponseBody> call = service.sendBody(jsonBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void  clientCall(){
        String url = "http://192.168.1.106/httptest/form.php";
        OkHttpClient okHttpClient = new OkHttpClient();

//        RequestBody body = new FormBody.Builder()
//                .add("name", "zhangsan")
//                .add("sex", "男")
//                .build();
        String type = "application/otcet-stream";
        RequestBody body = new MultipartBody.Builder()
                 .setType(MultipartBody.FORM)
                .addFormDataPart("img",file.getName(), RequestBody.create(MediaType.parse(type),file))
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okhttp3.Call call = okHttpClient.newCall(request);

        call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    Log.d("response",response.body().string());
                }
            });
           ;



    }
    public void place() {

        String url = "http://api.map.baidu.com/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
//             .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory( StringConverterFactory.create())
                .build();

        BDPlaceService placeService = retrofit.create(BDPlaceService.class);
        final String ak = "3SsBr80QjeaM56fwv8Q7FnAfouDm7GXI";
        final String mcode = "13:FE:86:52:59:CD:DC:02:AD:54:1F:E7:D3:CC:DC:62:54:19:14:2C;com.fxlc.zklm";
        Map<String, String> map = new HashMap<>();
        map.put("ak", ak);
        map.put("mcode", mcode);
        map.put("query", "天安门");
        map.put("region", "北京市");
        map.put("output", "json");
        map.put("city_limit", "true");

        Call<ResponseBody> call = placeService.suggest(map);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("response", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        try {
            URLEncoder.encode("", Xml.Encoding.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
