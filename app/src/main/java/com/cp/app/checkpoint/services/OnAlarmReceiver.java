package com.cp.app.checkpoint.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ahmed Yehya on 22/12/2017.
 */

public class OnAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakeIntentService.acquireStaticLock(context);
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }
}
