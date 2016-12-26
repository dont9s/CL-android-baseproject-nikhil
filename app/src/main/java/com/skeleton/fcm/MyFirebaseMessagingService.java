/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skeleton.fcm;


import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.skeleton.R;
import com.skeleton.activity.SplashActivity;
import com.skeleton.plugin.Foreground;

import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";
    private JSONObject jsonObject;
    private Dialog dialog;
    Intent intent;
    private static NotificationManager notificationManager;
    private static OnPushReceived onPushReceived;

    /*
          ==============setCallback is set in onResume of BaseActivity i.e set everytime the top activity in stack resumes i.e comes in foreground=============================
    */

    public static void setCallback(OnPushReceived mOnPushReceived) {
        onPushReceived = mOnPushReceived;
    }

    public static void clearNotification() {
        if (notificationManager != null)
            notificationManager.cancelAll();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        //   Log.d(TAG, "FCM Notification Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());
        Log.d(TAG, "FCM Data Message:(TA)G " + remoteMessage.getData().get("message"));

        /*
          ===============Foreground.get(getApplication()).isForeground() checks if the app is in foreground i.e visible not minimized or killed====================
          ===============if it is killed or minimized show notification============================
         */

        if (Foreground.get(getApplication()).isForeground())
            onPushReceived.onPush(remoteMessage.getData());
        else {
            showNotification(remoteMessage.getData());
        }


    }


    public void showNotification(Map<String, String> data) {
       Log.e("SHow notification = ", "showNotification");

        final Intent notificationIntent = new Intent(getApplicationContext(), SplashActivity.class);

        PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                //     .setTicker(r.getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("message")))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(r.getString(R.string.app_name))
                .setContentText(data.get("message"))
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .build();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }


}

