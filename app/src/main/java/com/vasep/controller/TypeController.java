package com.vasep.controller;

import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Category;
import com.vasep.models.CategoryResult;
import com.vasep.models.Type;
import com.vasep.models.TypeModel;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class TypeController {

    private ServiceInterface service;
    public TypeController(){
        service = ServiceGenerator.GetInstance();
    }

    public List<Type> getAllType(){
        Call<TypeModel> profile = service.getAllType();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            TypeModel str = profile.execute().body();
            if (str.getCode() == 200){
                return str.getTypes();
            }
        } catch (Exception ex) {
        }
        return null;
    }

}
