package com.vasep.models;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class Market {
    private String id;
    private String name;
    private String eng_name;

    private String create_date;

    public Market(String id, String name, String create_date) {
        this.id = id;
        this.name = name;
        this.create_date = create_date;
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

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }
}
