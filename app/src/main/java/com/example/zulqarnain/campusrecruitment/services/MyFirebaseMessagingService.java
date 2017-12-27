package com.example.zulqarnain.campusrecruitment.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.example.zulqarnain.campusrecruitment.ui.activities.MainActivity;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Zul Qarnain on 11/22/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String type=null;
        // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                showNotification(remoteMessage.getData().get("type"), remoteMessage.getData().get("vacancy"));
            }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }

        Log.d("", "From: " + remoteMessage.getFrom());
    }

    private void showNotification(String title, String vacancy) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New job of " + title + " has been added ")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Vanacy " + vacancy)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
