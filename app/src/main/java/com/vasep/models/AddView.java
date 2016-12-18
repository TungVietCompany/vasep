package com.vasep.models;

/**
 * Created by thuyetpham94 on 18/12/2016.
 */

public class AddView {
    private int code;
    private String user_id;

    public AddView(int code, String user_id) {
        this.code = code;
        this.user_id = user_id;
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
