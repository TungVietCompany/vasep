package com.vasep.models;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class CategoryResult {
    private int code;
    private List<Category> categories;

    public CategoryResult(int code, List<Category> categories) {
        this.code = code;
        this.categories = categories;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
