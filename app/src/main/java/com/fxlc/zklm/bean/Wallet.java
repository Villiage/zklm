package com.fxlc.zklm.bean;

import java.io.Serializable;

/**
 * Created by cyd on 2017/7/11.
 */

public class Wallet implements Serializable{

    private String  summoney;
    private String  usablemoney;
    private String  loanmoney;

    public String getSummoney() {
        return summoney;
    }

    public void setSummoney(String sumMoney) {
        this.summoney = sumMoney;
    }

    public String getUsablemoney() {
        return usablemoney;
    }

    public void setUsablemoney(String usablemoney) {
        this.usablemoney = usablemoney;
    }

    public String getLoanmoney() {
        return loanmoney;
    }

    public void setLoanmoney(String loanmoney) {
        this.loanmoney = loanmoney;
    }
}
