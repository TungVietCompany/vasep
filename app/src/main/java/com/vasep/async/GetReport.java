package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.activity.SpecialDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;
import com.vasep.notification.Information;
import com.vasep.recyclerclick.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class GetReport extends AsyncTask<Void, List<Article>, List<Article>> {

    private Context context;
    private int article_id;
    private int user_id;
    ProgressDialog dialog;

    public GetReport(Context context, int article_id, int user_id) {
        this.context = context;
        this.article_id = article_id;
        this.user_id = user_id;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(Information.loading);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        ArticleController articleController = new ArticleController();
        return articleController.getReport(article_id, user_id);
    }

    @Override
    protected void onPostExecute(final List<Article> articles) {

        try {
                if (articles.size() > 0) {
                    Intent intent = new Intent(context, ReportDetailActivity.class);
                    intent.putExtra("article", articles.get(0));
                    context.startActivity(intent);
                }
        } catch (Exception e) {
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }
}
