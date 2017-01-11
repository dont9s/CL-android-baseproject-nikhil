package com.skeleton.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skeleton.R;
import com.skeleton.database.CommonData;
import com.skeleton.fcm.MyFirebaseMessagingService;
import com.skeleton.fcm.OnPushReceived;
import com.skeleton.util.PermissionsHelper;

import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by kashish nalwa on 12/13/16.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, OnPushReceived {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    /*
     ======================is Foreground set to true in onResume and false in onPause
     to make sure popup shows only in foreground activity not in other underlying activities in stack.
                                                                    ===========================================
     */
    private boolean isForeground = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PermissionsHelper.REQUEST_CODE_ASK_PERMISSIONS)
            PermissionsHelper.setGrantResult(grantResults);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * on home activity
         */
        if (this instanceof TestActivity && CommonData.getPushData() != null) {
            showAlertDialog(CommonData.getPushData());
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
         /*
          ==============setCallback is set in onResume of BaseActivity i.e set everytime the top
          activity in stack resumes i.e comes in foreground=============================
         */
        MyFirebaseMessagingService.setCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onPush(final Map<String, String> data) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForeground) {
                    showAlertDialog(data);
                }

            }
        });

    }

    /*

    ==============showAlertDialog() method show popup on screen
    change it acc to app requirement======================

    */

    private void showAlertDialog(Map<String, String> data) {

        alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
        alertDialogBuilder.setPositiveButton(R.string.text_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                if (CommonData.getPushData() != null) {
                    CommonData.setPushData(null);
                }


            }
        }).setNegativeButton(R.string.text_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        }).setMessage(R.string.app_name);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
}
