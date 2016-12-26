/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skeleton.fcm;

import android.os.Handler;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.skeleton.database.CommonData;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private static FCMTokenInterface fcmTokenCallback;
    private static Handler handlerOs = new Handler();



    /**
     * The Application's current Instance ID token is no longer valid and thus a new one must be requested.
     */
    @Override
    public void onTokenRefresh() {
        // If you need to handle the generation of a token, initially or after a refresh this is
        // where you should do that.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + token);

        if (token != null)
            CommonData.setDeviceToken(token);

        if (token != null && fcmTokenCallback != null) {
            fcmTokenCallback.onTokenReceived(token);
            fcmTokenCallback = null;
            clearHandler();
        }
    }


    public static void setCallback(FCMTokenInterface callback) {



        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            if (token != null && !token.isEmpty()) {
                CommonData.setDeviceToken(token);
                callback.onTokenReceived(token);
                return;
            }
        } catch (Exception e) {
            Log.v("SetCallback EXP= ", e.toString());
        }
        fcmTokenCallback = callback;
        startHandler();
    }

    public static void retry(FCMTokenInterface callback)
    {
        setCallback(callback);
    }

    private static void startHandler() {
        handlerOs.postDelayed(new Runnable() {
            @Override
            public void run() {
                fcmTokenCallback.onFailure();
                fcmTokenCallback = null;
            }
        }, 20000);
    }

    private static void clearHandler() {
        handlerOs.removeCallbacksAndMessages(null);
    }



}