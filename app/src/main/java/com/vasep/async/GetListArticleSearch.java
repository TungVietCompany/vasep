package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.activity.SpecialDetailActivity;
import com.vasep.adapter.AdapterItem;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;
import com.vasep.notification.Information;
import com.vasep.recyclerclick.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class GetListArticleSearch extends AsyncTask<Void, Void, List<Article>> {

    private Context context;
    private int top;
    private int from;
    private int type;
    ProgressDialog dialog;
    public List<Article> list;
    AdapterItem adapterItem, adapterItemNew;
    RecyclerView rView;
    private int type_load;
    ImageView image_top;
    TextView txt_date_top, txt_title_top, txt_category_top;
    String category, title, language_type;
    int market_id, product_id, type_id;
    RelativeLayout screen1_tops;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ProgressBar progressBar;


    public GetListArticleSearch(Context context,AdapterItem adapterItemNew, String category, String title, RecyclerView rView, AdapterItem adapterItem, int type_load, int top, int from, int type, String language_type, int market_id, int product_id, int type_id) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.adapterItem = adapterItem;
        this.type_load = type_load;
        this.rView = rView;
        this.category = category;
        this.title = title;
        this.language_type = language_type;
        this.market_id = market_id;
        this.product_id = product_id;
        this.type_id = type_id;
        this.adapterItemNew = adapterItemNew;
    }

    public GetListArticleSearch(Context context, int top, int from, int type, AdapterItem adapterItem,
                                RecyclerView rView, int type_load, ImageView image_top,
                                TextView txt_date_top, TextView txt_title_top, TextView txt_category_top,
                                RelativeLayout screen1_tops, AdapterItem adapterItemNew, String language_type,
                                String category, String title, int market_id, int product_id, int type_id) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.adapterItem = adapterItem;
        this.rView = rView;
        this.type_load = type_load;
        this.image_top = image_top;
        this.txt_date_top = txt_date_top;
        this.txt_title_top = txt_title_top;
        this.txt_category_top = txt_category_top;
        this.language_type = language_type;
        this.category = category;
        this.title = title;
        this.market_id = market_id;
        this.product_id = product_id;
        this.type_id = type_id;
        this.screen1_tops = screen1_tops;
        this.adapterItemNew = adapterItemNew;

    }

    public GetListArticleSearch(Context context,ProgressBar progressBar,AdapterItem adapterItemNew, String category, String title, RecyclerView rView, AdapterItem adapterItem, int type_load, int top, int from, int type, String language_type, int market_id, int product_id, int type_id) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.adapterItem = adapterItem;
        this.type_load = type_load;
        this.rView = rView;
        this.category = category;
        this.title = title;
        this.language_type = language_type;
        this.market_id = market_id;
        this.product_id = product_id;
        this.type_id = type_id;
        this.adapterItemNew = adapterItemNew;
        this.progressBar = progressBar;
    }

    public GetListArticleSearch(Context context,ProgressBar progressBar, int top, int from, int type, AdapterItem adapterItem,
                                RecyclerView rView, int type_load, ImageView image_top,
                                TextView txt_date_top, TextView txt_title_top, TextView txt_category_top,
                                RelativeLayout screen1_tops, AdapterItem adapterItemNew, String language_type,
                                String category, String title, int market_id, int product_id, int type_id) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.adapterItem = adapterItem;
        this.rView = rView;
        this.type_load = type_load;
        this.image_top = image_top;
        this.txt_date_top = txt_date_top;
        this.txt_title_top = txt_title_top;
        this.txt_category_top = txt_category_top;
        this.language_type = language_type;
        this.category = category;
        this.title = title;
        this.market_id = market_id;
        this.product_id = product_id;
        this.type_id = type_id;
        this.screen1_tops = screen1_tops;
        this.adapterItemNew = adapterItemNew;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        ArticleController articleController = new ArticleController();
        return articleController.searchArticle(category, title, type, top, from, language_type, market_id, product_id, type_id);
    }

    @Override
    protected void onPostExecute(final List<Article> articles) {
        progressBar.setVisibility(View.GONE);
        try {
            if (articles.size() > 0) {

                Article article = new Article();
                SharedPreferences pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("is_search", true);
                editor.commit();
                if (type_load == 1) {

                    if (adapterItem.getType() == 1) {
                        image_top.setVisibility(View.VISIBLE);
                        screen1_tops.setVisibility(View.VISIBLE);
                        if (articles.size() > 1) {
                            article = articles.get(0);
                            adapterItem.addAll(articles.subList(1, articles.size()));
                            adapterItem.notifyDataSetChanged();
                            rView.setAdapter(adapterItem);
                        } else {
                            article = articles.get(0);
                            rView.setAdapter(adapterItemNew);
                        }
                    } else {
                        if (articles.size() > 0) {
                            adapterItem.addAll(articles);
                            adapterItem.notifyDataSetChanged();
                            rView.setAdapter(adapterItem);
                        }else {
                            rView.setAdapter(adapterItemNew);
                        }

                    }

                } else {
                    adapterItem.addItemMore(articles);
                    adapterItem.notifyDataSetChanged();
                }
                adapterItem.setMoreLoading(false);

                if (type == 2) {

                    /*bắt sự kiện click vào item reprort*/
                    rView.addOnItemTouchListener(new RecyclerItemClickListener(context,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(context, ReportDetailActivity.class);
                                    intent.putExtra("article", adapterItem.getList().get(position));
                                    context.startActivity(intent);
//                                        GetReport getReport = new GetReport(context,Integer.parseInt(adapterItem.getList().get(position).getId()),1);
//                                        getReport.execute();
                                }
                            }));

                } else if (type == 1) {

                    Picasso.with(context).load(article.getImage()).into(image_top);
                    txt_date_top.setText(ChangeDate.convertDate(article.getCreate_date()));
                    txt_category_top.setText(article.getCategory_name() + " | ");
                    txt_title_top.setText(article.getTitle());
                    final Article tmp= article;
                    image_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, SpecialDetailActivity.class);
                            intent.putExtra("article", tmp);
                            context.startActivity(intent);
                        }
                    });

                        /*bắt sự kiện click vào item*/
                    rView.addOnItemTouchListener(new RecyclerItemClickListener(context,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(context, SpecialDetailActivity.class);
                                    intent.putExtra("article", adapterItem.getList().get(position));
                                    context.startActivity(intent);
                                }
                            }));
                } else {

                    Picasso.with(context).load(article.getImage()).into(image_top);
                    txt_date_top.setText(ChangeDate.convertDate(article.getCreate_date()));
                    txt_category_top.setText(article.getCategory_name() + " | ");
                    txt_title_top.setText(article.getTitle());

                    final Article tmp= article;
                    image_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, NewsDetailActivity.class);
                            intent.putExtra("article", tmp);
                            context.startActivity(intent);
                        }
                    });
                        /*bắt sự kiện click vào item*/
                    rView.addOnItemTouchListener(new RecyclerItemClickListener(context,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(context, NewsDetailActivity.class);
                                    intent.putExtra("article", adapterItem.getList().get(position));
                                    context.startActivity(intent);
                                }
                            }));
                }
            } else {
                if(type_load==1) {
                    rView.setAdapter(adapterItemNew);
                    Toast.makeText(context,context.getResources().getString(R.string.nodata),Toast.LENGTH_SHORT).show();
                }
                image_top.setVisibility(View.GONE);
                screen1_tops.setVisibility(View.GONE);
            }

        } catch (Exception e) {

        }

    }


}
