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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *         put your fonts ttf files in assets/fonts package
         *          .setDefaultFontPath("fonts/Dosis-Medium.ttf") set the default font
         *          add style in styles.xml to further change the default font
         *          to change font just add "textApperance" in  xml and select the style you want to use.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Dosis-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

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
    public void onPush(Map<String, String> data) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForeground) {
                    showAlertDialog();
                }

            }
        });

    }

    /*

    ==============showAlertDialog() method show popup on screen
    change it acc to app requirement======================

    */

    private void showAlertDialog() {

        alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
        alertDialogBuilder.setPositiveButton(R.string.text_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();


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
