package com.vasep.models;

import java.io.Serializable;

/**
 * Created by Administrator on 27/12/2016.
 */

public class ReportItem implements Serializable {
    private String url;
    private String title;
    private String id;
    private String money_order;
    private String money_discount;
    private String money_total;

    public ReportItem() {
    }

    public ReportItem(String url, String title, String id, String money_order, String money_discount, String money_total) {
        this.url = url;
        this.title = title;
        this.id = id;
        this.money_order = money_order;
        this.money_discount = money_discount;
        this.money_total = money_total;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney_order() {
        return money_order;
    }

    public void setMoney_order(String money_order) {
        this.money_order = money_order;
    }

    public String getMoney_discount() {
        return money_discount;
    }

    public void setMoney_discount(String money_discount) {
        this.money_discount = money_discount;
    }

    public String getMoney_total() {
        return money_total;
    }

    public void setMoney_total(String money_total) {
        this.money_total = money_total;
    }
}
