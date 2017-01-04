package com.skeleton;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.skeleton.plugin.Foreground;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by kashish 12/12/16.
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
        /**
         * Initialize Paper db first berfore performing database addition or deletion
         */
        Paper.init(getApplicationContext());

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

/**
 * Initialize the SDK before executing any other operations,
 */
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.skeleton",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEY HASH ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }


}
