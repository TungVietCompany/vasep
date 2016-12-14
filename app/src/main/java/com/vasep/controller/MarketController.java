package com.vasep.controller;

import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Category;
import com.vasep.models.CategoryResult;
import com.vasep.models.Market;
import com.vasep.models.MarketResult;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class MarketController {
    private ServiceInterface service;
    public MarketController(){
        service = ServiceGenerator.GetInstance();
    }

    public List<Market> getCategory(){
        Call<MarketResult> profile = service.getMarket();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            MarketResult str = profile.execute().body();
            if (str.getCode() == 200){
                return str.getMarkets();
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
