package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.vasep.activity.ReportDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.controller.ArticleController;
import com.vasep.controller.Common;
import com.vasep.controller.ContactController;
import com.vasep.models.Article;
import com.vasep.notification.Information;
import com.vasep.recyclerclick.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class SearchArticle extends AsyncTask<Void,Void,List<Article>> {
    List<Article> list1;
    Context context;
    ProgressDialog dialog;
    String categories,title,language_type;
    RecyclerView rview;
    int type;
    AdapterItem adapterItem;
    public SearchArticle(Context context, String categories, String title,AdapterItem adapterItem, RecyclerView rview,int type,String language_type){
        this.context = context;
        this.title = title;
        this.categories = categories;
        this.rview = rview;
        this.type = type;
        this.adapterItem = adapterItem;
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
        //return contactController.searchArticle(categories,title,2, Common.LOAD_TOP,0,language_type);
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Article> list) {
        try{
            if(list.size()>0){
                list1 = new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getIs_special().equals(String.valueOf(type))){
                        list1.add(list.get(i));
                    }
                }
                adapterItem.addAll(list1);
                adapterItem.notifyDataSetChanged();
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }
}
