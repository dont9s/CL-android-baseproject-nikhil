package com.skeleton.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.util.dialog.DoubleBtnDialog;
import com.skeleton.util.dialog.SingleBtnDialog;

public class TestActivity extends BaseActivity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SingleBtnDialog.with(TestActivity.this).setMessage("Hello").setHeading("HEADING RIGHT").setOptionPositive("DONE").setCallback(new SingleBtnDialog.OnActionPerformed() {
            @Override
            public void positive() {
                Toast.makeText(TestActivity.this,"DONE",Toast.LENGTH_SHORT).show();
                DoubleBtnDialog.with(TestActivity.this).setMessage("DOUBLE MESSAGE").setHeading("DOUBLE HEADING").setCallback(new DoubleBtnDialog.OnActionPerformed() {
                    @Override
                    public void positive() {
                        Toast.makeText(TestActivity.this,"DONE",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void negative() {

                    }
                }).show();
            }
        }).show();


    }
}
