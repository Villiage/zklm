package com.fxlc.zklm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cyd on 2017/7/11.
 */

public class PayHistory implements Serializable{

   private String sum;
   private List<PayItem> paylist;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public List<PayItem> getPaylist() {
        return paylist;
    }

    public void setPaylist(List<PayItem> paylist) {
        this.paylist = paylist;
    }

   public class PayItem{


       private String id;
       private String incomeUser;
       private String payTime;
       private String paymentMoney;

       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getIncomeUser() {
           return incomeUser;
       }

       public void setIncomeUser(String incomeUser) {
           this.incomeUser = incomeUser;
       }

       public String getPayTime() {
           return payTime;
       }

       public void setPayTime(String payTime) {
           this.payTime = payTime;
       }

       public String getPaymentMoney() {
           return paymentMoney;
       }

       public void setPaymentMoney(String paymentMoney) {
           this.paymentMoney = paymentMoney;
       }
   }
}
