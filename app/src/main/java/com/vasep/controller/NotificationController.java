package com.vasep.controller;

import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Result;

import java.util.Hashtable;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 18/12/2016.
 */

public class NotificationController {
    private ServiceInterface service;
    public NotificationController(){
        service = ServiceGenerator.GetInstance();
    }

    public Boolean insertNoti(String firebase_id,String device_type){

//        Hashtable obj = new Hashtable();
//        obj.put("firebase_id",firebase_id);
//        obj.put("device_type",device_type);

        Call<Result> contact = service.noti(firebase_id,device_type);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Result str = contact.execute().body();
            if (str.getCode() == 200){
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }
}