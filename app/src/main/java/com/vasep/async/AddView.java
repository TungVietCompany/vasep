package com.vasep.async;

import android.content.Context;
import android.os.AsyncTask;

import com.vasep.controller.ArticleController;

/**
 * Created by thuyetpham94 on 20/12/2016.
 */

public class AddView extends AsyncTask<Void,Void,Boolean> {

    String article_id;
    Context context;

    public AddView(Context context,String article_id){
        this.context  = context;
        this.article_id = article_id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ArticleController articleController = new ArticleController();
        return articleController.addView(article_id);
    }
}
