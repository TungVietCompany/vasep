package com.vasep.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.PurchaseActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.activity.SignInActivity;
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
    RelativeLayout relative_crime;
    AppBarLayout appBarLayout;
    Activity activity;
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
                                RelativeLayout screen1_tops,RelativeLayout relative_crime, AppBarLayout appBarLayout, AdapterItem adapterItemNew, String language_type,
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
        this.relative_crime= relative_crime;
        this.appBarLayout= appBarLayout;
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

    public GetListArticleSearch(Activity activity,Context context, ProgressBar progressBar, int top, int from, int type, AdapterItem adapterItem,
                                RecyclerView rView, int type_load, ImageView image_top,
                                TextView txt_date_top, TextView txt_title_top, TextView txt_category_top,
                                RelativeLayout screen1_tops, RelativeLayout relative_crime, AppBarLayout appBarLayout, AdapterItem adapterItemNew, String language_type,
                                String category, String title, int market_id, int product_id, int type_id) {
        this.activity=activity;
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
        this.relative_crime= relative_crime;
        this.appBarLayout= appBarLayout;
    }

    @Override
    protected void onPreExecute() {
        try {
            progressBar.setVisibility(View.VISIBLE);
        }catch (Exception err){

        }
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        ArticleController articleController = new ArticleController();
         SharedPreferences pref =context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
         String user_id = pref.getString("user_id", "0").equals("")?"0":pref.getString("user_id", "0");
        return articleController.searchArticle(category, title, type, top, from, language_type, market_id, product_id, type_id, Integer.parseInt(user_id));
    }

    @Override
    protected void onPostExecute(final List<Article> articles) {

        try {
            if(null!=progressBar) {
                progressBar.setVisibility(View.GONE);
            }
            if (articles.size() > 0) {

                Article article = new Article();
                SharedPreferences pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("is_search", true);
                editor.commit();
                if (type_load == 1) {

                    if (type == 0||type==1) {
                        image_top.setVisibility(View.VISIBLE);
                        screen1_tops.setVisibility(View.VISIBLE);
                        relative_crime.setVisibility(View.VISIBLE);
                        ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
                        params.height = (int) context.getResources().getDimension(R.dimen.imageview_width);
                        appBarLayout.setLayoutParams(params);
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
                                    /*Intent intent = new Intent(context, ReportDetailActivity.class);
                                    intent.putExtra("article", adapterItem.getList().get(position));
                                    context.startActivity(intent);*/

                                }
                            }));

                } else if (type == 1) {

                    Picasso.with(context).load(article.getImage()).into(image_top);
                    txt_date_top.setText(ChangeDate.convertDate(article.getCreate_date()).trim());
                    txt_category_top.setText(article.getCategory_name() + " | ");
                    txt_title_top.setText(article.getTitle());
                    final Article tmp= article;
                    image_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(tmp.getReport()!=null) {
                                Intent intent = new Intent(context, ReportDetailActivity.class);
                                intent.putExtra("article",tmp);
                                context.startActivity(intent);
                            }else {
                                Intent intent = new Intent(context, SpecialDetailActivity.class);
                                intent.putExtra("article", tmp);
                                context.startActivity(intent);
                            }
                        }
                    });

                        /*bắt sự kiện click vào item*/
                    rView.addOnItemTouchListener(new RecyclerItemClickListener(context,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                }
                            }));
                } else {

                    Picasso.with(context).load(article.getImage()).into(image_top);
                    txt_date_top.setText(ChangeDate.convertDate(article.getCreate_date()).trim());
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

                                }
                            }));
                }
            } else {
                if(type_load==1) {
                    rView.setAdapter(adapterItemNew);
                    Toast.makeText(context,context.getResources().getString(R.string.nodata),Toast.LENGTH_SHORT).show();
                }
                ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                appBarLayout.setLayoutParams(params);
                relative_crime.setVisibility(View.GONE);
                image_top.setVisibility(View.GONE);
                screen1_tops.setVisibility(View.GONE);
            }

        } catch (Exception e) {

        }

    }


}
