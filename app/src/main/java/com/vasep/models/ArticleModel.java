package com.vasep.models;

import android.provider.MediaStore;

import java.util.List;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class ArticleModel {
    private int code;
    private List<Article> article;

    public ArticleModel(int code, List<Article> article) {
        this.code = code;
        this.article = article;
    }

    public ArticleModel() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }
}
