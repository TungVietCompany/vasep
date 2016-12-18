package com.vasep.async;

import android.content.Context;
import android.os.AsyncTask;

import com.vasep.controller.NotificationController;

/**
 * Created by thuyetpham94 on 18/12/2016.
 */

public class NotiAsync extends AsyncTask<Void,Void,Boolean> {
    Context context;
    String firebase_id,device_type;
    public NotiAsync(Context context,String firebase_id,String device_type){
        this.context = context;
        this.firebase_id = firebase_id;
        this.device_type = device_type;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        NotificationController notificationController = new NotificationController();
        return notificationController.insertNoti(firebase_id,device_type);
    }
}
