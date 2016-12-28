package com.skeleton.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by cl-mac-mini-3 on 12/14/16.
 */
public class CommonUtil {


    public static int PLAY_SERVICES_RESOLUTION_REQUEST=101;

    /**
     * Method to check if internet is connected
     *
     * @return
     */
    public static boolean isConnectedToInternet(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) (context.getApplicationContext())
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out
                    .println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

}
