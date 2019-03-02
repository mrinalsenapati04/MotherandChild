package com.sih.teamsquadra.motherandchild;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());
    }

    public void showNotification(String message) {
        //Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, UserRemainders.class), 0);
        Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.time_to_take_meds);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_mother)
                .setContentTitle("Mother's Notification")
                .setLights(Color.BLUE, 500, 500)
                .setContentText(message)
                .setContentIntent(pi)
                .setSound(uri)
                .setAutoCancel(true)
                //.setSound(notificationSound)
                .build();

        //notification.sound( Uri.parse("android.resource://"+ this.getPackageName() + "/" + R.raw.time_to_take_meds));
        //notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.time_to_take_meds);
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        // notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;


        // Get System Service for Audio Manager
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // Get MAX volume count for Notification stream
        int streamMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        // Set Ringer Mode to Normal
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        // Set Notification stream volume to MAX volume
        am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, streamMaxVolume, AudioManager.ADJUST_SAME);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);


        // ------------------------------------------------------------------

        //notification.sound = Uri.parse("android.resource://com.sih.teamsquadra.motherandchild/" + R.raw.time_to_take_meds);

    }
}