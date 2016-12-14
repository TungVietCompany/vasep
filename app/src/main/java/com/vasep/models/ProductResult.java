package com.vasep.models;

import java.util.List;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class ProductResult {
    private int code;
    private List<Market> products;

    public ProductResult(int code, List<Market> products) {
        this.code = code;
        this.products = products;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Market> getProducts() {
        return products;
    }

    public void setProducts(List<Market> products) {
        this.products = products;
    }
}
