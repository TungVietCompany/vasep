package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vasep.adapter.AdapterHome;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ContactController;
import com.vasep.models.Article;
import com.vasep.notification.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class SearchArticle extends AsyncTask<Void,Void,List<Article>> {

    Context context;
    ProgressDialog dialog;
    String categories,title;
    RecyclerView rview;
    int type;
    public SearchArticle(Context context, String categories, String title, RecyclerView rview,int type){
        this.context = context;
        this.title = title;
        this.categories = categories;
        this.rview = rview;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(Information.loading);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected List<Article> doInBackground(Void... params) {
        ArticleController contactController = new ArticleController();
        return contactController.searchArticle(categories,title);
    }

    @Override
    protected void onPostExecute(List<Article> list) {
        try{
            if(list.size()>0){
                List<Article> list1 = new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getIs_special().equals(String.valueOf(type))){
                        list1.add(list.get(i));
                    }
                }
                AdapterHome adapterHome = new AdapterHome(context,list1);
                rview.setAdapter(adapterHome);
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }
}
