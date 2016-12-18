package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.vasep.R;
import com.vasep.activity.SpecialDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterMenu;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.controller.CategoryController;
import com.vasep.models.Article;
import com.vasep.models.Category;
import com.vasep.notification.Information;
import com.vasep.recyclerclick.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllCategoryMenu extends AsyncTask<Void,Void,List<Category>>{

    Context context;
    RecyclerView rview;
    ProgressDialog dialog;
    int type;
    AdapterMenu adapterHome;
    List<Article> list;
    AdapterItem adapterItem;
    String language_type;

    public GetAllCategoryMenu(Context context, RecyclerView rview,AdapterItem adapterItem,AdapterMenu adapterHome, int type){
        this.context = context;
        this.rview = rview;
        this.type = type;
        this.adapterHome = adapterHome;

        this.adapterItem = adapterItem;

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
    protected void onPostExecute(final List<Category> categories) {
        try{
            if(categories.size() > 0){
                adapterHome.setCategories(categories);
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                String language = pref.getString("language", null);
                if(null==language||language.equals("vi")) {
                    for (int i=0; i<categories.size(); i++){
                        categories.get(i).setLanguage_type(0);
                    }
                    adapterHome = new AdapterMenu(context, categories);
                }else{
                    for (int i=0; i<categories.size(); i++){
                        categories.get(i).setLanguage_type(1);
                    }
                    adapterHome = new AdapterMenu(context, categories);
                }
                rview.setAdapter(adapterHome);
                final List<Article> list1 = new ArrayList<Article>();
                rview.addOnItemTouchListener(new RecyclerItemClickListener(context,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String category = categories.get(position).getId();
                                for(int i=0;i<adapterItem.getList().size();i++){
                                    if(adapterItem.getList().get(i).getCategory_id().equals(category) && adapterItem.getList().get(i).getLanguage_type().equals(language_type)){
                                        list1.add(adapterItem.getList().get(i));
                                    }
                                }

                                adapterItem.addAll(list1);
                                adapterItem.notifyDataSetChanged();
                            }
                        }));
            }else{
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }
}
