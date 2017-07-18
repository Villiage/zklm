package com.fxlc.zklm.test;

import com.fxlc.zklm.bean.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by cyd on 2017/4/18.
 */

public class JavaTest {

    private static String[] Provinces = {"京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "新", "浙", "赣", "桂",
            "甘", "晋", "蒙", "陕", "吉", "闵", "贵", "粤", "青", "藏", "川", "宁", "琼"};

    public void jsonformat() {
        String json = "[{\"check\":true,\"name\":\"华为客服\",\"num\":\"4008308300\"},{\"check\":true,\"name\":\"广陵\",\"num\":\"18435128214\"}]";

        List<Contact> cList = new Gson().fromJson(json, new TypeToken<List<Contact>>() {
        }.getType());
        System.out.println(cList.size());

    }

    public static void main(String[] args) {

        new JavaTest().jsonformat();

    }
}
