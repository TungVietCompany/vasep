package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vasep.controller.ArticleController;
import com.vasep.notification.Information;

/**
 * Created by thuyetpham94 on 18/12/2016.
 */

public class InsertView extends AsyncTask<Void,Void,Boolean> {

    Context context;
    int article_id;
    ProgressDialog dialog;
    public InsertView(Context context, int article_id){
        this.context = context;
        this.article_id = article_id;
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
    protected Boolean doInBackground(Void... params) {
        ArticleController articleController = new ArticleController();
        return articleController.addView(article_id);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        try{
            if(aBoolean==true){
                Toast.makeText(context,Information.insert_contact_success,Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

        }
        dialog.dismiss();
    }
}
