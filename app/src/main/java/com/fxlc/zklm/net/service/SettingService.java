package com.fxlc.zklm.net.service;

import com.fxlc.zklm.net.HttpResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cyd on 2017/7/12.
 */

public interface SettingService {
    @FormUrlEncoded
    @POST("base/saveFeedback")
    Call<HttpResult>    feedback(@Field("feedback") String feedback);

}
