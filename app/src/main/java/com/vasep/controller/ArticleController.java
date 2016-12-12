package com.vasep.controller;

import android.app.Activity;
import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Article;
import com.vasep.models.ArticleModel;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class ArticleController {

    private ServiceInterface service;
    Boolean success;
    Activity mActivity;
    public ArticleController(){
        service = ServiceGenerator.GetInstance();
    }

    public List<Article> getArticle(int top,int from,int type){
        Call<ArticleModel> profile = service.getArticle(top,from,type);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ArticleModel str = profile.execute().body();
            if (str.getCode() == 200){
                return str.getArticle();
            }
        } catch (Exception ex) {
        }
        return null;
    }

}
