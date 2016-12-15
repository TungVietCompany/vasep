package com.vasep.models;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class Type {
    private String id;
    private String name;
    private String create_date;
    private String number_report;
    private boolean isCheck;

    public Type(String id, String name, String create_date, String number_report, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.create_date = create_date;
        this.number_report = number_report;
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getNumber_report() {
        return number_report;
    }

    public void setNumber_report(String number_report) {
        this.number_report = number_report;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
