package com.fxlc.zklm.bean;

import java.util.List;

/**
 * Created by cyd on 2017/4/13.
 */

public class WeatherCity {
      private int status;
      private String msg;
      private List<City> result;

      public int getStatus() {
            return status;
      }

      public void setStatus(int status) {
            this.status = status;
      }

      public String getMsg() {
            return msg;
      }

      public void setMsg(String msg) {
            this.msg = msg;
      }

      public List<City> getResult() {
            return result;
      }

      public void setResult(List<City> result) {
            this.result = result;
      }
}
