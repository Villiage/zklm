package com.fxlc.zklm.bean;

import java.util.List;

/**
 * Created by cyd on 2017/7/12.
 */

public class LoanHistory {

    private List<LoanBean> loanlist;

    public List<LoanBean> getLoanlist() {
        return loanlist;
    }

    public void setLoanlist(List<LoanBean> loanlist) {
        this.loanlist = loanlist;
    }

    public static class LoanBean {


        private String id;
        private String money;
        private Object status;
        private List<RepayBean> repaylist;
        private String loantime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public List<RepayBean> getRepaylist() {
            return repaylist;
        }

        public void setRepaylist(List<RepayBean> repaylist) {
            this.repaylist = repaylist;
        }

        public String getLoantime() {
            return loantime;
        }

        public void setLoantime(String loantime) {
            this.loantime = loantime;
        }

        public static class RepayBean {
            /**
             * id : bbd8176673094960b60135e2104e6e7f
             * repaymoney : 5
             * freemoney : null
             */

            private String id;
            private String repaymoney;
            private Object freemoney;
            private String repaytime;
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRepaymoney() {
                return repaymoney;
            }

            public void setRepaymoney(String repaymoney) {
                this.repaymoney = repaymoney;
            }

            public Object getFreemoney() {
                return freemoney;
            }

            public void setFreemoney(Object freemoney) {
                this.freemoney = freemoney;
            }

            public String getRepaytime() {
                return repaytime;
            }

            public void setRepaytime(String repaytime) {
                this.repaytime = repaytime;
            }
        }
    }
}
