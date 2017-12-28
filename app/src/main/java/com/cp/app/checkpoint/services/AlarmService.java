package com.cp.app.checkpoint.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.ui.listtoorder.ListToOrderActivity;

/**
 * Created by Ahmed Yehya on 22/12/2017.
 */

public class AlarmService extends WakeIntentService {
    Notification myNotication;
    public AlarmService() {
        super("AlarmService");
    }

    @Override
    void doReminderWork(Intent intent) {
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, ListToOrderActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSound(defaultSoundUri);
        builder.setContentTitle("waiting for you");
        builder.setContentText("You order is ready");
        builder.setSmallIcon(R.drawable.logo_boarder);
        builder.setContentIntent(pi);
        builder.build();
        int id = 123456789;
        manager.notify(id, builder.build());

    }
}
