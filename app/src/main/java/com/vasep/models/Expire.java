package com.vasep.models;

/**
 * Created by Administrator on 23/12/2016.
 */

public class Expire {
    int code;
    String date;

    public Expire() {
    }

    public Expire(int code, String date) {
        this.code = code;
        this.date = date;
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
