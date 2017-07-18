package com.fxlc.zklm.bean;

import java.util.List;

/**
 * Created by cyd on 2017/7/14.
 */

public class PayInfo {


    private PayviewBean payview;

    public PayviewBean getPayview() {
        return payview;
    }

    public void setPayview(PayviewBean payview) {
        this.payview = payview;
    }

    public static class PayviewBean {


        private String incomeUser;
        private String incomeMobile;
        private String paytime;
        private String paymentMoney;
        private List<String> billImg;

        public String getIncomeUser() {
            return incomeUser;
        }

        public void setIncomeUser(String incomeUser) {
            this.incomeUser = incomeUser;
        }

        public String getIncomeMobile() {
            return incomeMobile;
        }

        public void setIncomeMobile(String incomeMobile) {
            this.incomeMobile = incomeMobile;
        }

        public String getPaytime() {
            return paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getPaymentMoney() {
            return paymentMoney;
        }

        public void setPaymentMoney(String paymentMoney) {
            this.paymentMoney = paymentMoney;
        }

        public List<String> getBillImg() {
            return billImg;
        }

        public void setBillImg(List<String> billImg) {
            this.billImg = billImg;
        }
    }
}
