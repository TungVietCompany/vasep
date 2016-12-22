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

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "password=h&username=hp");
        Request request = new Request.Builder()
                .url("http://103.237.147.54/rest_vasep/rest/login?password=h&username=hp")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "d1463fe6-c60e-66c1-c945-6f4abab78853")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
