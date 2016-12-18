package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vasep.controller.ArticleController;
import com.vasep.controller.ContactController;
import com.vasep.notification.Information;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class ContactAsync extends AsyncTask<Void,Void,Boolean> {

    Context context;
    ProgressDialog dialog;
    String fullname,phone,address,note;
    public ContactAsync(Context context,String fullname,String phone,String address,String note){
        this.context = context;
        this.fullname = fullname;
        this.phone = phone;
        this.address =address;
        this.note =note;
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
        ContactController contactController = new ContactController();
        return contactController.insertContact(fullname,phone,address,note);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        try{
            if(aBoolean == true){
                Toast.makeText(context,Information.insert_contact_success,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,Information.insert_contact_fail,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}
