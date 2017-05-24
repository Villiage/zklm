package com.fxlc.zklm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cyd on 2017/4/13.
 */
@Entity
public class City {
    @Id
    private Long id;
    @Property(nameInDb = "cityid")
    @Index(unique = true)
    private String cityid;
    @Property(nameInDb = "parentid")
    private String parentid;
    @Property(nameInDb = "citycode")
    private String citycode;
    @Property(nameInDb = "city")
    private String city;



    @Generated(hash = 2084612746)
    public City(Long id, String cityid, String parentid, String citycode,
            String city) {
        this.id = id;
        this.cityid = cityid;
        this.parentid = parentid;
        this.citycode = citycode;
        this.city = city;
    }

    @Generated(hash = 750791287)
    public City() {
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
