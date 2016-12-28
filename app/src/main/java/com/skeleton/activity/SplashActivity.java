package com.skeleton.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.skeleton.R;
import com.skeleton.fcm.FCMTokenInterface;
import com.skeleton.fcm.MyFirebaseInstanceIdService;
import com.skeleton.util.CommonUtil;

public class SplashActivity extends BaseActivity implements FCMTokenInterface {

    public final int PLAY_SERVICES_RESOLUTION_REQUEST = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * when updated play services the control  comes back to onActivity result
         * call init again to get Device token
         */
        switch (requestCode) {
            case PLAY_SERVICES_RESOLUTION_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    init();
                }

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();


    }


    public void init() {
        if (!checkPlayServices(SplashActivity.this)) {
            return;
        }
        if (!CommonUtil.isConnectedToInternet(SplashActivity.this)) {
            //Error dialog
            return;
        }
        MyFirebaseInstanceIdService.setCallback(this);
    }

    @Override
    public void onTokenReceived(final String token) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("DEVICE TOKEN---------", token);
                /**
                 * perform everything here like
                 * your access token login or navigation to SignIn SignUp page
                 */

                Intent intent = new Intent(SplashActivity.this, TestActivity.class);
                startActivity(intent);


            }
        });
    }


    @Override
    public void onFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("DEVICE TOKEN---------", "NULL");
                /**
                 * in failure make another attempt to get device token
                 * or finish activity which in turn finish application.
                 */
                MyFirebaseInstanceIdService.retry(SplashActivity.this);
            }
        });
    }


    private boolean checkPlayServices(Activity context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {

                apiAvailability.getErrorDialog(context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {

                //Show error dialog
                Log.i("SplashActivity", "This device is not supported.");

            }
            return false;
        }
        return true;
    }

}