package com.fxlc.zklm.bean;

import java.io.Serializable;

/**
 * Created by cyd on 2017/6/19.
 */

public class UInfo implements Serializable {

    private UserBean user;
     private IDcard idcard;
     private  Company company;
    public UserBean getUser() {
        return user;
    }

    public IDcard getIdcard() {
        return idcard;
    }

    public void setIdcard(IDcard idcard) {
        this.idcard = idcard;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public class UserBean {

        private String mobile;
        private int pstatus;
        private int comstatus;

        public int getPstatus() {
            return pstatus;
        }

        public void setPstatus(int pstatus) {
            this.pstatus = pstatus;
        }

        public int getComstatus() {
            return comstatus;
        }

        public void setComstatus(int comstatus) {
            this.comstatus = comstatus;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public class IDcard {
        private String name;
        private String cardnumber;
        private String cardAddress;
        private String cardorgan;
        private String cardstartdate;
        private String cardenddate;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public void setCardnumber(String cardnumber) {
            this.cardnumber = cardnumber;
        }

        public String getCardAddress() {
            return cardAddress;
        }

        public void setCardAddress(String cardAddress) {
            this.cardAddress = cardAddress;
        }

        public String getCardorgan() {
            return cardorgan;
        }

        public void setCardorgan(String cardorgan) {
            this.cardorgan = cardorgan;
        }

        public String getCardstartdate() {
            return cardstartdate;
        }

        public void setCardstartdate(String cardstartdate) {
            this.cardstartdate = cardstartdate;
        }

        public String getCardenddate() {
            return cardenddate;
        }

        public void setCardenddate(String cardenddate) {
            this.cardenddate = cardenddate;
        }
    }

    public class Company implements  Serializable{

        private String legalPerson ;
        private String companyName;
        private String businessLicense;

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }
    }
}
