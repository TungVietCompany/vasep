package com.vasep.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.vasep.api.ServiceGenerator;
import com.vasep.api.ServiceInterface;
import com.vasep.models.Expire;
import com.vasep.models.Payment;
import com.vasep.models.PaymentResult;
import com.vasep.models.Result;
import com.vasep.models.User;

import java.util.ArrayList;
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

    public Boolean CheckUserExpire(int user_id,int report_id){
        Call<Expire> contact = service.checkUserExpire(user_id,report_id);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Expire str = contact.execute().body();
            if (str.getCode() == 200){
                if(Integer.parseInt(str.getDate())>0) {
                    return true;
                }
            }
        } catch (Exception ex) {
            String err= ex.getMessage();
        }
        return false;
    }

    public Boolean InserTransaction(int article_id,int user_id,int payment_id,int status,int error_code,String message,String transaction_id,String developer_trans_id,String amount,String currency,String url,String bank){
        Call<Result> contact = service.insertTransaction(article_id, user_id, payment_id, status, error_code, message, transaction_id, developer_trans_id, amount, currency, url,bank);
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

    public ArrayList<Payment> GetPayment(){
        Call<PaymentResult> contact = service.getAllPayment();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            PaymentResult str = contact.execute().body();
            if (str.getCode() == 200){

                return str.getPayments();
            }
        } catch (Exception ex) {
            String err= ex.getMessage();
        }
        return new ArrayList<Payment>();
    }
}
