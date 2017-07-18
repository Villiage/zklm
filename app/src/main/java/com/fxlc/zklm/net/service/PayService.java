package com.fxlc.zklm.net.service;

import com.fxlc.zklm.bean.BankAccount;
import com.fxlc.zklm.bean.LoanHistory;
import com.fxlc.zklm.bean.PayHistory;
import com.fxlc.zklm.bean.PayInfo;
import com.fxlc.zklm.bean.Wallet;
import com.fxlc.zklm.net.HttpResult;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by cyd on 2017/7/7.
 */

public interface PayService {
    @POST("user/payment")
    Call<HttpResult> pay(@QueryMap Map<String, String> map);

    @POST("user/verificationPaypass")
    Call<HttpResult> verifypwd(@Query("payPass") String pwd);

    @Multipart
    @POST("base/saveWaybill")
    Call<HttpResult> saveBillImg(@Query("payId") String payid, @PartMap Map<String, RequestBody> map);

    @GET("user/userMoney")
    Call<HttpResult<Wallet>> userMoney();

    @GET("user/paymentList")
    Call<HttpResult<PayHistory>> payList(@Query("year") String year, @Query("month") String month);

    @GET("base/loanlist")
    Call<HttpResult<LoanHistory>> loanList(@Query("year") String year, @Query("month") String month);

    @GET("user/bankCard")
    Call<HttpResult<BankAccount>> bank();
    @GET("user/paymentInfo")
    Call<HttpResult<PayInfo>> payInfo(@Query("payId") String payId);

}
