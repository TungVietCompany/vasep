package com.vasep.async;

import android.content.Context;
import android.content.SharedPreferences;
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

    List<Market> list;

    public List<Market> getList() {
        return list;
    }

    public void setList(List<Market> list) {
        this.list = list;
    }

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

                setList(categories);
                String[] values=new String[categories.size()+1];
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final String language = pref.getString("language", "vi");
                if(language.equals("vi")) {
                    values[0]="Tất cả";
                    for (int i = 0; i < categories.size(); i++) {
                        values[i+1] = categories.get(i).getName();
                    }
                }else{
                    values[0]="All";
                    for (int i = 0; i < categories.size(); i++) {
                        values[i+1] = categories.get(i).getEng_name();
                    }
                }
                lv.setMinValue(0);
                lv.setMaxValue(values.length-1);
                lv.setValue(0);
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
