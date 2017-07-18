package com.fxlc.zklm.bean;

import java.io.Serializable;

/**
 * Created by cyd on 2017/6/14.
 */

public class User implements Serializable{
    String id;
    String name;
    String mobile;
    String token;
    int psstatu;
    int companystatus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPsstatu() {
        return psstatu;
    }

    public void setPsstatu(int psstatu) {
        this.psstatu = psstatu;
    }

    public int getCompanystatus() {
        return companystatus;
    }

    public void setCompanystatus(int companystatus) {
        this.companystatus = companystatus;
    }
}
