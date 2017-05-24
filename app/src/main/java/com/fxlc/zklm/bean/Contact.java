package com.fxlc.zklm.bean;

/**
 * Created by cyd on 2017/4/27.
 */

public class Contact {
    private String name;
    private String num;
    private Character first;
    public Contact() {
    }

    public Contact(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Character getFirst() {
        return first;
    }

    public void setFirst(Character first) {
        this.first = first;
    }
}
