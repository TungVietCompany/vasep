package com.vasep.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.vasep.adapter.AdapterLvMaketing;
import com.vasep.controller.MarketController;
import com.vasep.controller.ProductController;
import com.vasep.models.Market;
import com.vasep.notification.Information;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllProduct extends AsyncTask<Void,Void,List<Market>>{

    Context context;
    NumberPicker lv;
    public GetAllProduct(Context context, NumberPicker lv){
        this.context = context;
        this.lv = lv;
    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected List<Market> doInBackground(Void... params) {
        ProductController categoryController = new ProductController();
        return categoryController.getCategory();
    }

    @Override
    protected void onPostExecute(List<Market> categories) {
        try{
            if(categories.size() > 0){
                int index=0;
                String[] values=new String[categories.size()];
                for (int i=0; i<categories.size(); i++){
                   /* if(categories.get(i).getName().equals("Thuỷ sản"));{
                        index=i;
                    }*/
                    values[i]= categories.get(i).getName();
                }
                lv.setMinValue(1);
                lv.setMaxValue(values.length-1);
                lv.setValue(9);
                lv.setDisplayedValues(values);
                lv.setWrapSelectorWheel(false);
            }else{
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }
}
