package com.vasep.controller;

import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Market;
import com.vasep.models.MarketResult;
import com.vasep.models.ProductResult;

import java.util.List;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class ProductController {
    private ServiceInterface service;
    public ProductController(){
        service = ServiceGenerator.GetInstance();
    }

    public List<Market> getCategory(){
        Call<ProductResult> profile = service.getProduct();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ProductResult str = profile.execute().body();
            if (str.getCode() == 200){
                return str.getProducts();
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
