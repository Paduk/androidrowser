package com.example.hanjun.version_zero;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class Everynotifi extends NotificationListenerService {
    public static final String TAG = "Main1";
    Context context;

    @Override

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "Posted");
        String pack = sbn.getPackageName();
        String ticker = sbn.getNotification().tickerText.toString();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        //Log.d(TAG,pack+","+ticker+","+title+","+text);

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);

        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");
        Log.d(TAG, "Removed");
    }
}
