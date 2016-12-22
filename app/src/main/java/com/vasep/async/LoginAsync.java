package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vasep.R;
import com.vasep.controller.ConnectApp;
import com.vasep.controller.ContactController;
import com.vasep.notification.Information;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 22/12/2016.
 */

public class LoginAsync extends AsyncTask<Void,Void,Boolean> {

    Context context;
    ProgressDialog dialog;
    String username,pass;
    public LoginAsync(Context context,String username,String pass){
        this.context = context;
        this.username = username;
        this.pass = pass;

    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(Information.loading);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ConnectApp contactController = new ConnectApp(context);
        return contactController.Login(username,pass);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        try{
            if(aBoolean == true){
               // Toast.makeText(context,context.getResources().getString(android.support.design.R.string),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,context.getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}
