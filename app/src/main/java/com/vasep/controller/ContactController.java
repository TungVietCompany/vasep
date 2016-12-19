package com.vasep.controller;

import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Result;

import java.util.Hashtable;

import retrofit2.Call;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class ContactController {
    private ServiceInterface service;
    Boolean success;
    public ContactController(){
        service = ServiceGenerator.GetInstance();
    }

    public Boolean insertContact(String fullname,String phone,String address,String note){

//        Hashtable obj = new Hashtable();
//        obj.put("fullname",fullname);
//        obj.put("phone",phone);
//        obj.put("address",address);
//        obj.put("note",note);

        Call<Result> contact = service.insertContact(fullname,phone,address,note);
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
