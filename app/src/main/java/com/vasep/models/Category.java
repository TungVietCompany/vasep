package com.vasep.models;

import java.io.Serializable;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class Category implements Serializable {
    private String id;
    private String name;
    private String eng_name;
    private String create_date;
    private String description;
    private boolean ischeck = false;
    private String image;
    private int language_type;

    public int getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(int language_type) {
        this.language_type = language_type;
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

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category(String id, String name, String eng_name, String create_date, String description, boolean ischeck, String image) {
        this.id = id;
        this.name = name;
        this.eng_name = eng_name;
        this.create_date = create_date;
        this.description = description;
        this.ischeck = ischeck;
        this.image = image;
    }
}
