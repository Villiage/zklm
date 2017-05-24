package com.fxlc.zklm.net.service;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by cyd on 2017/4/25.
 */

public interface BDPlaceService {
     @GET("place/v2/suggestion")
     Call<ResponseBody>  suggest(@QueryMap Map<String, String> options);
}
