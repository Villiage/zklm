package com.fxlc.zklm.test;

import com.fxlc.zklm.bean.Box;
import com.fxlc.zklm.bean.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by cyd on 2017/6/21.
 */

public interface PhpService {

    @GET("xx.php")
    Call<ResponseBody>   con();
    @POST("bean.php")
    Call<ResponseBody>   sendBean(@Body User user);
    @POST("beanset.php")
    Call<ResponseBody>   sendSet(@Body Box box);
    @POST("json.php")
    Call<ResponseBody>   sendJson(@Query("json") String json);

    @Multipart
    @POST("body.php")
    Call<ResponseBody>   sendBody(@Part("str") RequestBody body);
    @Multipart
    @POST("form.php")
    Call<ResponseBody>   sendFile(@Part RequestBody body);
    @Multipart
    @POST("file.php")
    Call<ResponseBody>   sendFile2(@Part MultipartBody.Part body);
}
