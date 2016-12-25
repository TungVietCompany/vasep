package com.vasep.firabase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vasep.R;
import com.vasep.activity.MainActivity;


/**
 * Created by duong on 9/20/2016.
 */
public class FireBaseService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String ss= remoteMessage.getNotification().getBody();
        String id = remoteMessage.getNotification().getSound();
        sendNotification(remoteMessage.getNotification().getBody(),title,id);

    }

    private void sendNotification(String messageBody,String title,String id) {
        Intent intent = new Intent( this , MainActivity.class );
        intent.putExtra("id_type",id+"");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app);
        Bitmap thumb=Bitmap.createBitmap(100,132, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(thumb);
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
                new Rect(0,0,thumb.getWidth(),thumb.getHeight()),null);
        Drawable drawable = new BitmapDrawable(getResources(),thumb);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_app)
                .setLargeIcon(((BitmapDrawable)drawable).getBitmap())
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(5)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
