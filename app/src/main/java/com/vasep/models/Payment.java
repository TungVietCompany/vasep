package com.vasep.models;

/**
 * Created by Administrator on 22/12/2016.
 */

public class Payment {
    String id;
    String name;
    String create_date;
    String price;
    String date_number;
    String eng_name;

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate_number() {
        return date_number;
    }

    public void setDate_number(String date_number) {
        this.date_number = date_number;
    }
}
