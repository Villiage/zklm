package com.fxlc.zklm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cyd on 2017/4/13.
 */

public class WeatherInfo implements Serializable{

    private List<City> result;

    public List<City> getResult() {
        return result;
    }
}
