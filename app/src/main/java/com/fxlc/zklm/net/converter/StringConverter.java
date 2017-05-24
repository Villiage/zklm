package com.fxlc.zklm.net.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by cyd on 2017/4/14.
 */

public class StringConverter implements Converter<ResponseBody,String>{


    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
