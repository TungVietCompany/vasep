package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.vasep.R;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.controller.CategoryController;
import com.vasep.controller.Common;
import com.vasep.models.Category;
import com.vasep.notification.Information;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllCategory extends AsyncTask<Void,Void,List<Category>>{

    Context context;
    AdapterRecylerSearch adapterRecylerSearch;
    RecyclerView rview;
    ProgressDialog dialog;
    DialogPlus dialogPlus;
    ImageView image_top;
    TextView txt_date_top, txt_title_top, txt_category_top;
    AdapterItem mAdapter,mAdapterNew;
    RecyclerView recyclerView;
    RelativeLayout screen1_tops;
    int type_article;
    RelativeLayout relative_crime;
    AppBarLayout appBarLayout;

    public GetAllCategory(Context context, RecyclerView rview, DialogPlus dialogPlus, AdapterItem mAdapter, AdapterItem mAdapterNew, RecyclerView recyclerView, int type_article, ImageView image_top, TextView txt_date_top, TextView txt_title_top, TextView txt_category_top, RelativeLayout screen1_tops,RelativeLayout relative_crime,AppBarLayout appBarLayout){
        this.context = context;
        this.rview = rview;
        this.dialogPlus = dialogPlus;
        this.mAdapter=mAdapter;
        this.mAdapterNew= mAdapterNew;
        this.recyclerView=recyclerView;
        this.type_article=type_article;
        this.image_top = image_top;
        this.txt_date_top = txt_date_top;
        this.txt_title_top = txt_title_top;
        this.txt_category_top = txt_category_top;
        this.screen1_tops = screen1_tops;
        this.relative_crime= relative_crime;
        this.appBarLayout= appBarLayout;
    }


    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(Information.loading);
        dialog.setIndeterminate(true);
        dialog.show();
    }


    @Override
    protected List<Category> doInBackground(Void... params) {
        CategoryController categoryController = new CategoryController();
        return categoryController.getCategory();
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        try{
            if(categories.size() > 0){
                final TextView txt_search = (TextView)dialogPlus.findViewById(R.id.screen10_btn_search);
                final TextView screen10_txt_search = (TextView)dialogPlus.findViewById(R.id.screen10_txt_search);
                adapterRecylerSearch = new AdapterRecylerSearch(context,categories);
                rview.setAdapter(adapterRecylerSearch);

                txt_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = context.getSharedPreferences("MyPref",context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("title",screen10_txt_search.getText().toString());
                        editor.commit();
                        String id = "";
                        try{
                            for (int i=0;i <adapterRecylerSearch.getCategories().size();i++){
                                if(adapterRecylerSearch.getCategories().get(i).ischeck()){
                                    id += adapterRecylerSearch.getCategories().get(i).getId()+",";
                                }
                            }
                            if(id.length()>0){
                                id = id.substring(0,id.length()-1);
                            }
                        }catch (Exception e){
                        }
                        editor.putString("catalog",id);
                        editor.commit();
                        int type_report = pref.getInt("type_report", -1);
                        String language = pref.getString("language", "vi");
                        String catalog = pref.getString("catalog", "");
                        String title = pref.getString("title", "");
                        int marketID= pref.getInt("marketID",-1);
                        int productID= pref.getInt("productID",-1);
                        if(type_article==0){
                            mAdapter.setType(1);
                            GetListArticleSearch getListArticleSearch = new GetListArticleSearch(context, Common.LOAD_TOP, 0, 0, mAdapter, recyclerView, 1, image_top,  txt_date_top,  txt_title_top,  txt_category_top, screen1_tops,relative_crime,appBarLayout,mAdapterNew, language, catalog, title, 0, 0, 0);
                            getListArticleSearch.execute();
                        }
                        if(type_article==1){
                            mAdapter.setType(1);
                            GetListArticleSearch getListArticleSearch = new GetListArticleSearch(context, Common.LOAD_TOP, 0, 1, mAdapter, recyclerView, 1, image_top,  txt_date_top,  txt_title_top,  txt_category_top, screen1_tops,relative_crime,appBarLayout,mAdapterNew, language, catalog, title, 0, 0, 0);
                            getListArticleSearch.execute();
                        }else if(type_article==2) {
                            mAdapter.setType(0);
                            GetListArticleSearch getListArticleSearch = new GetListArticleSearch(context, mAdapterNew, catalog, title, recyclerView, mAdapter, 1, Common.LOAD_TOP, 0, 2, language, marketID, productID, type_report);
                            getListArticleSearch.execute();
                        }
                        dialogPlus.dismiss();
                    }
                });
            }else{
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }
}
