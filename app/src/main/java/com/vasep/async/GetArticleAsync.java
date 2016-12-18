package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.activity.SpecialDetailActivity;
import com.vasep.controller.ArticleController;
import com.vasep.models.Article;
import com.vasep.notification.Information;

import java.util.List;

/**
 * Created by thuyetpham94 on 19/12/2016.
 */

public class GetArticleAsync extends AsyncTask<Void,Void,List<Article>> {
    Context context;
    String id,type;
    ProgressDialog dialog;
    public GetArticleAsync(Context context,String id,String type){
        this.context = context;
        this.id = id;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(Information.loading);
        dialog.setIndeterminate(true);
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected List<Article> doInBackground(Void... params) {
        ArticleController articleController = new ArticleController();
        return articleController.getArticlebyid(id);
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        try{
            if(articles.size() > 0){
                Intent intent;
                if(type.equals("0")){
                    intent = new Intent(context, SpecialDetailActivity.class);
                    intent.putExtra("article",articles.get(0));
                    context.startActivity(intent);
                }else if(type.equals("1")){
                    intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("article",articles.get(0));
                    context.startActivity(intent);
                }else if(type.equals("2")){
                    intent = new Intent(context, ReportDetailActivity.class);
                    intent.putExtra("article",articles.get(0));
                    context.startActivity(intent);
                }
            }
        }catch (Exception e){

        }
    }
}
