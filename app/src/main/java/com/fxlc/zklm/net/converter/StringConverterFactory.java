package com.fxlc.zklm.net.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by cyd on 2017/4/14.
 */

public final class StringConverterFactory extends Converter.Factory {
    private StringConverterFactory() {


    }

    public static StringConverterFactory create() {


        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringConverter();
    }
}
