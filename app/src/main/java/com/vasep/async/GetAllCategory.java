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
    RecyclerView rview;
    ProgressDialog dialog;
    DialogPlus dialogPlus;
    public GetAllCategory(Context context, RecyclerView rview, DialogPlus dialogPlus){
        this.context = context;
        this.rview = rview;
        this.dialogPlus = dialogPlus;
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
                TextView txt_search = (TextView)dialogPlus.findViewById(R.id.screen10_btn_search);
                adapterRecylerSearch = new AdapterRecylerSearch(context,categories);
                rview.setAdapter(adapterRecylerSearch);


                txt_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        String ss = id;
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
