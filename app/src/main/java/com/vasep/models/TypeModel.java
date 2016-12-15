package com.vasep.models;

import java.util.List;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class TypeModel {
    private int code;
    private List<Type> types;

    public TypeModel(int code, List<Type> types) {
        this.code = code;
        this.types = types;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
