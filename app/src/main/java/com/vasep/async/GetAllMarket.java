package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.orhanobut.dialogplus.DialogPlus;
import com.vasep.adapter.AdapterLvMaketing;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.controller.CategoryController;
import com.vasep.controller.MarketController;
import com.vasep.models.Category;
import com.vasep.models.Market;
import com.vasep.notification.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllMarket extends AsyncTask<Void, Void, List<Market>> {

    Context context;
    WheelPicker wheelLeft;
    List<Market> list;

    public List<Market> getList() {
        return list;
    }

    public void setList(List<Market> list) {
        this.list = list;
    }

    public GetAllMarket(Context context, WheelPicker lv) {
        this.context = context;
        this.wheelLeft = lv;
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
        try {
            if (categories.size() > 0) {
                List<String> list = new ArrayList<>();
                setList(categories);
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final String language = pref.getString("language", "vi");
                if (language.equals("vi")) {
                    list.add("Tất cả");
                    for (int i = 0; i < categories.size(); i++) {
                        list.add(categories.get(i).getName());
                    }
                } else {
                    list.add("All");
                    for (int i = 0; i < categories.size(); i++) {
                        list.add(categories.get(i).getEng_name());

                    }
                }
                wheelLeft.setData(list);
                wheelLeft.setItemTextSize(45);
                wheelLeft.setCyclic(true);
            } else {
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }
}
