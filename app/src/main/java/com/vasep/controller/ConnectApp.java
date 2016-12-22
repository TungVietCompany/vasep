package com.vasep.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Result;
import com.vasep.models.User;

import java.util.Hashtable;

import retrofit2.Call;

/**
 * Created by Administrator on 22/12/2016.
 */

public class ConnectApp {
    private ServiceInterface service;
    Boolean success;
    Context context;
    public ConnectApp(Context ct){
        this.context=ct;
        service = ServiceGenerator.GetInstance();
    }

    public Boolean Login(String username,String password){

        Call<User> contact = service.login(username,password);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            User str = contact.execute().body();
            if (str.getCode() == 200){
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("user_id",str.getUser_id());
                editor.putString("pass",password);
                editor.commit();
                return true;
            }
        } catch (Exception ex) {
            String err= ex.getMessage();
        }
        return false;
    }

    public Boolean Forgot(String email){
        Call<Result> contact = service.forgotPass(email);
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
            String err= ex.getMessage();
        }
        return false;
    }

    public Boolean SignUp(String username,String password, String fullName,String email){
        Call<User> contact = service.signUp(username,password,fullName,email);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            User str = contact.execute().body();
            if (str.getCode() == 200){
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("user_id",str.getUser_id());
                editor.putString("pass",password);
                editor.commit();
                return true;
            }
        } catch (Exception ex) {
            String err= ex.getMessage();
        }
        return false;
    }

    public Boolean ChangePass(String user_id,String password){
        Call<User> contact = service.changePass(user_id,password);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            User str = contact.execute().body();
            if (str.getCode() == 200){
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("pass",password);
                editor.commit();
                return true;
            }
        } catch (Exception ex) {
            String err= ex.getMessage();
        }
        return false;
    }
}
