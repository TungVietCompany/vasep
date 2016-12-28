package com.vasep.models;

/**
 * Created by Administrator on 23/12/2016.
 */

public class Expire {
    int code;
    String date;
    int buy_type;
    public Expire() {
    }

    public Expire(int code, String date) {
        this.code = code;
        this.date = date;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
