package com.vasep.models;

/**
 * Created by Administrator on 22/12/2016.
 */

public class User {
    private int code;
    private String user_id ;

    public User(int code, String user_id) {
        this.code = code;
        this.user_id = user_id;
    }

    public User() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
