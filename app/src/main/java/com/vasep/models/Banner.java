package com.vasep.models;

/**
 * Created by Administrator on 28/12/2016.
 */

public class Banner {
    String id;
    String name;
    String image;
    String create_date;
    String link;
    public Banner() {

    }

    public Banner(String id, String name, String image, String create_date, String link) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.create_date = create_date;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
