package com.fxlc.zklm.bean;

import java.util.List;

/**
 * Created by cyd on 2017/7/13.
 */

public class BankAccount {

    private List<BanklistBean> banklist;

    public List<BanklistBean> getBanklist() {
        return banklist;
    }

    public void setBanklist(List<BanklistBean> banklist) {
        this.banklist = banklist;
    }

    public static class BanklistBean {
        /**
         * bankName : 工商银行
         * bankNo : 6222020502017623021
         * openCard : 太原迎新街支行
         * name : 郭某人
         */

        private String bankName;
        private String bankNo;
        private String openCard;
        private String name;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public String getOpenCard() {
            return openCard;
        }

        public void setOpenCard(String openCard) {
            this.openCard = openCard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
