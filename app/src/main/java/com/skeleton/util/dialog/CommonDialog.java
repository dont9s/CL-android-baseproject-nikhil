package com.skeleton.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.skeleton.R;

/**
 * Created by Kashish nalwa.
 */
public class CommonDialog {

    public static Dialog twoBtnDialog;
    public static Dialog singleBtnDialog;

    /**
     * @param context
     * @param headingMsg
     * @param msg
     * @param optionNegative
     * @param optionPositive
     * @param heading
     * @param onActionPerformed
     *
     * How to use  :---
     *                  if you want to show single button dialog :
     *                  CommonDialog.signleBtnDialog(TestActivity.this, "this is heading", "this is message", true, new CommonDialog.OnActionPerformed() {
     *                  @Override public void positive() {
     *                          perform the action here on click
     *                      }
     *                     @Override public void negative() {
     *                      in this case this is of no concern to you
     *                      }
     *                     });
     *
     *                     if you want to show double button dialog :
     *
     *                      CommonDialog.doubleBtnDialog(TestActivity.this, "this is heading", "this is message", "no", "yes", true, new CommonDialog.OnActionPerformed() {
     *                      @Override public void positive() {
     *                      perform the action here on click of positive button
     *                      }
     *                      @Override public void negative() {
     *                      perform the action here on click of negative button
     *                       }
     *                      });
     */

    public static void doubleBtnDialog(final Context context, String headingMsg,
                                       String msg, String optionNegative, String optionPositive,
                                       boolean heading, final OnActionPerformed onActionPerformed) {

        if (twoBtnDialog != null) {
            twoBtnDialog.dismiss();
        }

        // custom dialog
        twoBtnDialog = new Dialog(context);
        twoBtnDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        twoBtnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        twoBtnDialog.setContentView(R.layout.custom_double_btn_layout);
        twoBtnDialog.setCancelable(false);
        twoBtnDialog.setCanceledOnTouchOutside(false);

        // set the custom dialog components - text, image and button
        TextView tvHeading = (TextView) twoBtnDialog.findViewById(R.id.tv_heading);
        tvHeading.setText(headingMsg);
        TextView tvMessage = (TextView) twoBtnDialog.findViewById(R.id.tv_message);
        tvMessage.setText(msg);
        Button btnYes = (Button) twoBtnDialog.findViewById(R.id.btn_yes);
        btnYes.setText(optionPositive);
        Button btnNo = (Button) twoBtnDialog.findViewById(R.id.btn_no);
        btnNo.setText(optionNegative);

        if (heading) {
            tvHeading.setVisibility(View.VISIBLE);
        } else {
            tvHeading.setVisibility(View.GONE);
        }


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoBtnDialog.dismiss();
                twoBtnDialog = null;

                if (onActionPerformed != null)
                    onActionPerformed.positive();

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoBtnDialog.dismiss();
                twoBtnDialog = null;

                if (onActionPerformed != null)
                    onActionPerformed.negative();

            }
        });

        twoBtnDialog.show();

    }


    public static void signleBtnDialog(final Context context, String headingMsg,
                                       String msg, boolean heading,
                                       final OnActionPerformed onActionPerformed) {
        if (singleBtnDialog != null) {
            singleBtnDialog.dismiss();
        }
        // custom dialog
        singleBtnDialog = new Dialog(context);
        singleBtnDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        singleBtnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        singleBtnDialog.setContentView(R.layout.custom_single_btn_layout);
        singleBtnDialog.setCancelable(false);
        singleBtnDialog.setCanceledOnTouchOutside(false);

        // set the custom dialog components - text, image and button
        TextView tvHeading = (TextView) singleBtnDialog.findViewById(R.id.tv_heading);
        tvHeading.setText(headingMsg);
        TextView tvMessage = (TextView) singleBtnDialog.findViewById(R.id.tv_message);
        tvMessage.setText(msg);
        Button btnOk = (Button) singleBtnDialog.findViewById(R.id.btn_ok);


        if (heading) {
            tvHeading.setVisibility(View.VISIBLE);
        } else {
            tvHeading.setVisibility(View.GONE);
        }


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleBtnDialog.dismiss();
                singleBtnDialog = null;
                if (onActionPerformed != null) {
                    onActionPerformed.positive();
                }

            }
        });


        singleBtnDialog.show();
    }


    public interface OnActionPerformed {
        void positive();

        void negative();

    }

}
