package com.fxlc.zklm.net.service;

import com.fxlc.zklm.bean.Contact;
import com.fxlc.zklm.bean.MyContact;
import com.fxlc.zklm.net.HttpResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by cyd on 2017/6/20.
 */

public interface ContactService {

    @POST("contact/saveContact")
    Call<HttpResult> addContact (@QueryMap HashMap<String ,String> param, @Body List<Contact> list);
   @POST("contact/saveContact")
    Call<HttpResult> addContact2 (@QueryMap HashMap<String ,String> param);
    @POST("contact/saveContact")
    Call<HttpResult> addContact3 (@Query("contact") String contact);
    @GET("contact/list")
    Call<HttpResult<MyContact>>  getContact();
    @GET("contact/update")
    Call<HttpResult>  update (@Query("id") String id, @Query("name")String name ,@Query("phone")String phone);
    @GET("contact/delete")
    Call<HttpResult>  del(@Query("id") String id);
    @GET("user/idStatus")
    Call<ResponseBody>    idStatus(@Query("mobile") String mobile);

}
