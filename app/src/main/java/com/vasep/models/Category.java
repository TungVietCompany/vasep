package com.vasep.models;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class Category {
    private String id;
    private String name;
    private String create_date;
    private String description;

    public Category(String id, String name, String create_date, String description) {
        this.id = id;
        this.name = name;
        this.create_date = create_date;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
