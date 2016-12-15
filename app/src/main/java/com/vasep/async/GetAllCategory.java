package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.vasep.R;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.controller.CategoryController;
import com.vasep.models.Category;
import com.vasep.notification.Information;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllCategory extends AsyncTask<Void,Void,List<Category>>{

    Context context;
    AdapterRecylerSearch adapterRecylerSearch;
    RecyclerView rview,rviewarticle;
    ProgressDialog dialog;
    DialogPlus dialogPlus;
    int type;

    AdapterItem adapterItem;
    boolean isSearch;
    String category_id,txt_search_new;
    String id = "";
    String txt;
    public GetAllCategory(Context context,boolean isSearch, String category_id, String txt_search_new, RecyclerView rview,RecyclerView rviewarticle,AdapterItem adapterItem, DialogPlus dialogPlus,int type){
        this.context = context;
        this.rview = rview;
        this.dialogPlus = dialogPlus;
        this.rviewarticle = rviewarticle;
        this.type = type;
        this.adapterItem = adapterItem;
        this.isSearch = isSearch;
        this.category_id = category_id;
        this.txt_search_new = txt_search_new;
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

                txt = screen10_txt_search.getText().toString();
                rview.setAdapter(adapterRecylerSearch);

                txt_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                        GetListArticleSearch getListArticle = new GetListArticleSearch(context,id,txt,rview,adapterItem,1, 6,0,2);
                        getListArticle.execute();
                        isSearch = true;
                        category_id = id;
                        txt_search_new = txt;
                        //dialogPlus.dismiss();
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
