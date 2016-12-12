package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;
import com.vasep.notification.Information;
import com.vasep.recyclerclick.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class GetListArticle extends AsyncTask<Void, List<Article>, List<Article>> {

    private Context context;
    private int top;
    private int from;
    private int type;
    ProgressDialog dialog;
    AdapterHome adapterHome;
    RecyclerView rview;
    ImageView image_top;
    TextView txt_date_top, txt_title_top, txt_category_top;
    GridLayoutManager gridLayoutManager;
    public GetListArticle(Context context, int top, int from, int type, RecyclerView rview, ImageView image_top, TextView txt_date_top, TextView txt_category_top, TextView txt_title_top) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.rview = rview;
        this.image_top = image_top;
        this.txt_date_top = txt_date_top;
        this.txt_title_top = txt_title_top;
        this.txt_category_top = txt_category_top;
        this.type = type;
    }

    public GetListArticle(Context context, int top, int from, int type, RecyclerView rview) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.rview = rview;
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
        return articleController.getArticle(top, from, type);
    }

    @Override
    protected void onPostExecute(final List<Article> articles) {

        try {
                if (articles.size() > 0) {
                    gridLayoutManager = new GridLayoutManager(context, 2);
                    rview.setLayoutManager(gridLayoutManager);

                    adapterHome = new AdapterHome(context, articles);
                    rview.setAdapter(adapterHome);

                    if (type == 2) {


                /*bắt sự kiện click vào item reprort*/
                        rview.addOnItemTouchListener(new RecyclerItemClickListener(context,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent intent = new Intent(context, ReportDetailActivity.class);
                                        intent.putExtra("article", articles.get(position));
                                        context.startActivity(intent);
                                    }
                                }));

                    }else {
                        Picasso.with(context).load(articles.get(0).getImage()).into(image_top);
                        txt_date_top.setText(ChangeDate.convertDate(articles.get(0).getCreate_date()));
                        txt_category_top.setText(articles.get(0).getCategory_name() + " | ");
                        txt_title_top.setText(articles.get(0).getTitle());
                /*bắt sự kiện click vào item*/
                        rview.addOnItemTouchListener(new RecyclerItemClickListener(context,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent intent = new Intent(context, NewsDetailActivity.class);
                                        intent.putExtra("article", articles.get(position));
                                        context.startActivity(intent);
                                    }
                                }));
                    }
                }
        } catch (Exception e) {
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }
}
