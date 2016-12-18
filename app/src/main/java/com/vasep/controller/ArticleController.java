package com.vasep.controller;

import android.app.Activity;
import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Article;
import com.vasep.models.ArticleModel;
import com.vasep.models.Result;
import com.vasep.models.AddView;

import java.util.HashMap;
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

    public List<Article> getReport(int article_id,int user_id){
        Call<ArticleModel> report = service.getReport(article_id,user_id);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ArticleModel str = report.execute().body();
            if (str.getCode() == 200){
                return str.getArticle();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public List<Article> searchArticle(String categories,String title,int type,int top,int from){
        Call<ArticleModel> search = service.searchArticle(categories,title,type,top,from);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ArticleModel str = search.execute().body();
            if (str.getCode() == 200){
                return str.getArticle();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public List<Article> filterArticle(String market_id,String product_id,int type_id,int top,int from){
        Call<ArticleModel> filter = service.filterArticle(market_id,product_id,type_id,top,from);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ArticleModel str = filter.execute().body();
            if (str.getCode() == 200){
                return str.getArticle();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public Boolean addView(int article_id){
        HashMap obj = new HashMap();
        obj.put("article_id",article_id);

        Call<AddView> contact = service.inserView(obj);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            AddView str = contact.execute().body();
            if (str.getCode() == 200){
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

}
