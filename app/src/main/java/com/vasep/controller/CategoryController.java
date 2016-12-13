package com.vasep.controller;

import android.app.Activity;
import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Article;
import com.vasep.models.ArticleModel;
import com.vasep.models.Category;
import com.vasep.models.CategoryResult;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class CategoryController {
    private ServiceInterface service;
    public CategoryController(){
        service = ServiceGenerator.GetInstance();
    }

    public List<Category> getCategory(){
        Call<CategoryResult> profile = service.allCategory();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            CategoryResult str = profile.execute().body();
            if (str.getCode() == 200){
                return str.getCategories();
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
