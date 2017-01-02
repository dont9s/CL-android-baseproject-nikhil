package com.skeleton.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.util.MRequiredPermissions;

public class TestActivity extends BaseActivity {

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MRequiredPermissions.REQUEST_CODE_ASK_PERMISSIONS)
            MRequiredPermissions.setGrantResult(grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MRequiredPermissions.permissions(TestActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}).checkPermissions(new MRequiredPermissions.PermissionClassCallback() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(TestActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void notGranted() {
                        Toast.makeText(TestActivity.this, "NOT DONE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 3000);


    }
}
