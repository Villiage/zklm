package com.fxlc.zklm.net;

/**
 * Created by cyd on 2017/7/20.
 */

public class MyThrowable extends Throwable{

    @Override
    public String getMessage() {
        return " response data error";
    }
}
