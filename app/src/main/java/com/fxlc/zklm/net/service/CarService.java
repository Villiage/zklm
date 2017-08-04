package com.fxlc.zklm.net.service;

import com.fxlc.zklm.bean.MyCars;
import com.fxlc.zklm.net.HttpResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by cyd on 2017/6/23.
 */

public interface CarService {
     @Multipart
     @POST("base/saveTruck")
    Call<HttpResult> saveTruck(@QueryMap Map<String ,String> map, @Part MultipartBody.Part driveImg1,  @Part MultipartBody.Part driveImg2, @Part MultipartBody.Part driveImg3,@Part MultipartBody.Part manageImg,@Part MultipartBody.Part handdriveImg1,@Part MultipartBody.Part handdriveImg2,@Part MultipartBody.Part handdriveImg3,@Part MultipartBody.Part handmanageImg);


    @GET("base/getTruck")
    Call<ResponseBody> getTrucks();
    @GET("base/getTruck")
    Call<HttpResult<MyCars>> getTrucksNew();
}
