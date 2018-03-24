package com.cp.app.checkpoint.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.listtoorder.ListToOrderActivity;

/**
 * Created by Ahmed Yehya on 22/12/2017.
 */

public class AlarmService extends WakeIntentService {

    AlarmPresenter alarmPresenter;
    public AlarmService() {
        super("AlarmService");
    }

    @Override
    void doReminderWork(Intent intent) {

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        alarmPresenter = new AlarmPresenter(dataManager);
        alarmPresenter.clearTimer();

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.logosel);

        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, ListToOrderActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSound(defaultSoundUri);
        builder.setContentTitle(getString(R.string.notifi_title));
        builder.setContentText(getString(R.string.notifi_content));
        builder.setSmallIcon(R.drawable.logo_boarder);
        builder.setLargeIcon(icon);
        builder.setContentIntent(pi);
        builder.build();
        int id = 123456789;
        manager.notify(id, builder.build());

    }

}
