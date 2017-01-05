package com.skeleton.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by cl-mac-mini-3 on 1/2/17.
 */

public class PermissionsHelper {
    /**
     -------------How to use-----------------
     PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
     Manifest.permission.CAMERA}).checkPermissions(TestActivity.this, new PermissionsHelper.OnPermissionResult() {
    @Override
    public void onGranted() {
    Perform whatever you want to do here
    }

    @Override
    public void notGranted() {
    Perform whatever you want to do here
    }
    });



     Add this line of code in the base activity in onRequestPermissionsResult method
     @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
     if (requestCode == PermissionsHelper.REQUEST_CODE_ASK_PERMISSIONS)
     PermissionsHelper.setGrantResult(grantResults);
     }
     */


    /**
     * variables used
     */
    public static String[] permissionsList;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 0x100;
    public static OnPermissionResult callback;

/**
     * constructor
     *
     * @param permissionsList
     */
    public PermissionsHelper(String... permissionsList) {
        this.permissionsList = permissionsList;
        Log.v("SIZE", permissionsList.length + "");
    }

    public static PermissionsHelper permissions(String... permissionsList) {
        return new PermissionsHelper(permissionsList);
    }

    public static void setGrantResult(int[] grantResults) {
        boolean notGrantedBool = false;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGrantedBool = true;
                break;
            }
        }

        if (!notGrantedBool) {
            if (callback != null) {
                callback.onGranted();
            }
        } else {
            if (callback != null) {
                callback.notGranted();
            }
        }
    }

    public void checkPermissions(Activity activity,OnPermissionResult callback) {

        this.callback = callback;
        boolean notGrantedBool = false;
        for (int i = 0; i < permissionsList.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, permissionsList[i]) != PackageManager.PERMISSION_GRANTED) {
                notGrantedBool = true;
                break;
            }
        }

        if (notGrantedBool) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(permissionsList,
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
            this.callback.onGranted();
        }

    }

    public interface OnPermissionResult {
//        public void alreadyGranted();

        public void onGranted();

        public void notGranted();
    }

}
