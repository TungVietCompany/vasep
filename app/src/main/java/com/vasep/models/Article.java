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

    public Article(String id, String title, String create_date, String content, String category_id, String image, String last_update, String price, String language_type, String category_name) {
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
    }

    public Article() {
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
