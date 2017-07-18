package com.fxlc.zklm.net.service;

import com.fxlc.zklm.bean.UInfo;
import com.fxlc.zklm.bean.User;
import com.fxlc.zklm.net.HttpResult;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by cyd on 2017/6/14.
 */

public interface UserService {
     @POST("register/registerUser")
     Call<HttpResult>  reg ( @QueryMap Map<String, String> param);
     @POST("applogin/loginCheck")
     Call<HttpResult<User>>  login (@QueryMap Map<String, String> param);

     @POST("user/forgetPass")
     Call<String>  setpwd ( @QueryMap Map<String, String> param);
    @GET("sms/getSms")
    Call<HttpResult>  getSms (@Query("mobile") String mobile);
     @Multipart
    @POST("zklmUserCard/saveUserCard")
    Call<HttpResult>  saveIDcard (@Part("pcardpositive")RequestBody face,@Part("pcardreverse")RequestBody back ,  @QueryMap Map<String, String> param);

    @Multipart
    @POST("zklmUserCard/saveUserCard")
    Call<HttpResult>  saveIDcard2 (@Part MultipartBody.Part face, @Part MultipartBody.Part back , @QueryMap Map<String, String> param);



    @GET("user/getUserInformation")
    Call<HttpResult<UInfo>>  getUInfo (@QueryMap Map<String, String> param);

    @Multipart
    @POST("companyInfo/saveCompanyInfo")
    Call<ResponseBody>   saveCompany(@QueryMap Map<String,String> param,@Part("businessLicense") RequestBody lincense);
   @Multipart
    @POST("companyInfo/saveCompanyInfo")
    Call<HttpResult>   saveCompany2(@QueryMap Map<String,String> param,@Part MultipartBody.Part lincense);

    @POST("user/savePaypass")
    Call<HttpResult>    savePayPass(@QueryMap HashMap<String,String> map);


 }
