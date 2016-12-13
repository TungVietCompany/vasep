package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;
import com.vasep.notification.Information;
import com.vasep.recyclerclick.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class GetListArticleNew extends AsyncTask<Void, Void, List<Article>> {

    private Context context;
    private int top;
    private int from;
    private int type;
    ProgressDialog dialog;
    public List<Article> list;
    AdapterItem adapterItem;
    private  int type_load;
    public GetListArticleNew(Context context,AdapterItem adapterItem,int type_load, int top, int from, int type) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.adapterItem = adapterItem;
        this.type_load = type_load;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        ArticleController articleController = new ArticleController();
        return articleController.getArticle(top, from, type);
    }

    @Override
    protected void onPostExecute(final List<Article> articles) {
        try {
                if (articles.size() > 0) {
                    if(type_load == 1){
                        adapterItem.addAll(articles);
                        adapterItem.notifyDataSetChanged();
                    }else{
                        adapterItem.addItemMore(articles);
                        adapterItem.notifyDataSetChanged();

                    }
                    adapterItem.setMoreLoading(false);
                }

        } catch (Exception e) {
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }


}
