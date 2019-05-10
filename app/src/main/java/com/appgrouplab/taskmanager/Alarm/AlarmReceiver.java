package com.appgrouplab.taskmanager.Alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.appgrouplab.taskmanager.Manager.view.ManagerActivity;
import com.appgrouplab.taskmanager.R;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 5000;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();

        Intent notificationIntent = new Intent(context, ManagerActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        long[] pattern = new long[]{2000,1000,2000};



        NotificationCompat.Builder note = new NotificationCompat.Builder(context,"CANAL_TASKMANAGER")
                .setSmallIcon(R.drawable.calendar)
                .setContentTitle("Tienes una tarea pendiente")
                .setContentText(b.get("title").toString())
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setVibrate(pattern)
                .setContentIntent(contentIntent);


        Random random = new Random();
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(random.nextInt(),note.build());
    }



}
