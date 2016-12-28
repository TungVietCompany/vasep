package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterMenu;
import com.vasep.controller.CategoryController;
import com.vasep.controller.ConnectApp;
import com.vasep.models.Article;
import com.vasep.models.Banner;
import com.vasep.models.Category;
import com.vasep.notification.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 28/12/2016.
 */

public class BannerAsync extends AsyncTask<Void,Void,List<Banner>> {

    Context context;
    RecyclerView rview;
    ProgressDialog dialog;
    int type;
    AdapterMenu adapterHome;
    List<Article> list;
    AdapterItem adapterItem;
   ImageView imageView;
    int index;
    public BannerAsync(Context context, ImageView imageView, int index){
        this.context = context;
        this.imageView=imageView;
        this.index=index;

    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected ArrayList<Banner> doInBackground(Void... params) {
        ConnectApp categoryController = new ConnectApp(context);
        return categoryController.GetBanner();
    }

    @Override
    protected void onPostExecute(final List<Banner> categories) {
        try{
            if(categories.size() > 0){
                if (index < categories.size()) {
                    Glide.with(context).load(categories.get(index).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                }else{
                    Glide.with(context).load(categories.get(index-categories.size()).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                }

            }else{
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }
}
