package com.fxlc.zklm.bean;

import java.io.Serializable;

/**
 * Created by cyd on 2017/4/27.
 */

public class Contact implements Serializable{
    private String id;
    private String name;
    private String phone;
    private Character first;
    public boolean check;
    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Character getFirst() {
        return first;
    }

    public void setFirst(Character first) {
        this.first = first;
    }
}
