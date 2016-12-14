package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.vasep.R;
import com.vasep.adapter.AdapterLvMaketing;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.controller.CategoryController;
import com.vasep.controller.MarketController;
import com.vasep.models.Category;
import com.vasep.models.Market;
import com.vasep.notification.Information;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllMarket extends AsyncTask<Void,Void,List<Market>>{

    Context context;
    ListView lv;
    public GetAllMarket(Context context, ListView lv){
        this.context = context;
        this.lv = lv;
    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected List<Market> doInBackground(Void... params) {
        MarketController categoryController = new MarketController();
        return categoryController.getCategory();
    }

    @Override
    protected void onPostExecute(List<Market> categories) {
        try{
            if(categories.size() > 0){
                AdapterLvMaketing adapter = new AdapterLvMaketing(context,categories);
                lv.setAdapter(adapter);
            }else{
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }
}
