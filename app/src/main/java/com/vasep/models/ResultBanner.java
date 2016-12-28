package com.vasep.models;

import java.util.ArrayList;

/**
 * Created by Administrator on 28/12/2016.
 */

public class ResultBanner {
    int code;
    ArrayList<Banner> advertises;

    public ResultBanner(int code, ArrayList<Banner> advertises) {
        this.code = code;
        this.advertises = advertises;
    }

    public ResultBanner() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Banner> getAdvertises() {
        return advertises;
    }

    public void setAdvertises(ArrayList<Banner> advertises) {
        this.advertises = advertises;
    }
}
