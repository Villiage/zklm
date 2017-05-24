package com.fxlc.zklm.net.service;

import com.fxlc.zklm.bean.WeatherCity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by cyd on 2017/4/13.
 */

public interface WeatherCityService {

      @GET("/weather/city")
      Call<WeatherCity>  listCity(@Header("Authorization") String Authorization);


}
