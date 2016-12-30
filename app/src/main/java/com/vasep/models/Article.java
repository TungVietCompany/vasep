package com.vasep.models;

import java.io.Serializable;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class Article implements Serializable{
    private String id;
    private String title;
    private String create_date;
    private String content;
    private String category_id;
    private String image;
    private String last_update;
    private String price;
    private String language_type;
    private String category_name;
    private String category_name_eng;
    private String discount;

    private String report;
    private String report_main;
    private String is_special;
    private String is_lock;
    private String price_online;
    private String price_download;

    public Article(String id, String title, String create_date, String content, String category_id, String image, String last_update, String price, String language_type, String category_name, String category_name_eng, String discount, String report, String report_main, String is_special, String is_lock, String price_online, String price_download) {
        this.id = id;
        this.title = title;
        this.create_date = create_date;
        this.content = content;
        this.category_id = category_id;
        this.image = image;
        this.last_update = last_update;
        this.price = price;
        this.language_type = language_type;
        this.category_name = category_name;
        this.category_name_eng = category_name_eng;
        this.discount = discount;
        this.report = report;
        this.report_main = report_main;
        this.is_special = is_special;
        this.is_lock = is_lock;
        this.price_online = price_online;
        this.price_download = price_download;
    }

    public String getCategory_name_eng() {
        return category_name_eng;
    }

    public void setCategory_name_eng(String category_name_eng) {
        this.category_name_eng = category_name_eng;
    }

    public Article() {
    }

    public String getReport_main() {
        return report_main;
    }

    public void setReport_main(String report_main) {
        this.report_main = report_main;
    }

    public String getPrice_online() {
        return price_online;
    }

    public void setPrice_online(String price_online) {
        this.price_online = price_online;
    }

    public String getPrice_download() {
        return price_download;
    }

    public void setPrice_download(String price_download) {
        this.price_download = price_download;
    }

    public String getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }

    public String getIs_special() {
        return is_special;
    }

    public void setIs_special(String is_special) {
        this.is_special = is_special;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
