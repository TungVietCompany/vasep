package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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
public class GetListArticleFilter extends AsyncTask<Void, Void, List<Article>> {

    private Context context;
    private int top;
    private int from;
    private int type;
    ProgressDialog dialog;
    public List<Article> list;
    AdapterItem adapterItem;
    RecyclerView rView;
    private  int type_load;
    ImageView image_top;
    TextView txt_date_top, txt_title_top, txt_category_top;
    String product_id,market_id;
    public GetListArticleFilter(Context context, String product_id, String market_id, RecyclerView rView, AdapterItem adapterItem, int type_load, int top, int from, int type) {
        this.context = context;
        this.top = top;
        this.from = from;
        this.type = type;
        this.adapterItem = adapterItem;
        this.type_load = type_load;
        this.rView=rView;
        this.product_id = product_id;
        this.market_id = market_id;
    }

    public GetListArticleFilter(Context context, int top, int from, int type, AdapterItem adapterItem, RecyclerView rView, int type_load, ImageView image_top, TextView txt_date_top, TextView txt_title_top, TextView txt_category_top) {
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
        return articleController.filterArticle(market_id,product_id,type,top, from);
    }

    @Override
    protected void onPostExecute(final List<Article> articles) {
        try {
                if (articles.size() > 0) {

                    SharedPreferences pref = context.getSharedPreferences("MyPref",context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("is_filter",true);
                    editor.commit();

                    if(type_load == 1){
                        adapterItem.addAll(articles);
                        adapterItem.notifyDataSetChanged();

                    }else{
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

                    }else if(type == 1){
                        Picasso.with(context).load(adapterItem.getList().get(0).getImage()).into(image_top);
                        txt_date_top.setText(ChangeDate.convertDate(articles.get(0).getCreate_date()));
                        txt_category_top.setText(adapterItem.getList().get(0).getCategory_name() + " | ");
                        txt_title_top.setText(adapterItem.getList().get(0).getTitle());
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
                    }else{
                        Picasso.with(context).load(adapterItem.getList().get(0).getImage()).into(image_top);
                        txt_date_top.setText(ChangeDate.convertDate(adapterItem.getList().get(0).getCreate_date()));
                        txt_category_top.setText(adapterItem.getList().get(0).getCategory_name() + " | ");
                        txt_title_top.setText(adapterItem.getList().get(0).getTitle());
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
                    }
                }
            else{

                }

        } catch (Exception e) {

        }
        dialog.dismiss();
    }


}
