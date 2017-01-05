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
 * Created by cl-mac-mini-3 on 1/4/17.
 */

public class DoubleBtnDialog {
    private Dialog dialog;

    private DoubleBtnDialog(Context context) {
        if (dialog != null) {
            dialog.dismiss();
        }
        // custom dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_double_btn_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    public DoubleBtnDialog setCancelable(boolean bool) {
        if (dialog != null) {
            dialog.setCancelable(false);
        }
        return this;
    }

    public DoubleBtnDialog setCancelableOnTouchOutside(boolean bool) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
        }
        return this;
    }


    public static DoubleBtnDialog with(Context context) {
        return new DoubleBtnDialog(context);
    }

    public DoubleBtnDialog setMessage(String msg) {
        if (dialog != null) {
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
            tvMessage.setText(msg);
        }
        return this;
    }

    public DoubleBtnDialog setHeading(String heading) {
        if (dialog != null) {
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_heading);
            tvMessage.setText(heading);
            tvMessage.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public DoubleBtnDialog setOptionPositive(String optionPositive) {
        if (dialog != null) {
            Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
            btnYes.setText(optionPositive);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    public DoubleBtnDialog setOptionNegative(String optionNegative) {
        if (dialog != null) {
            Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
            btnNo.setText(optionNegative);
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    public DoubleBtnDialog setCallback(final OnActionPerformed onActionPerformed) {
        if (dialog != null && onActionPerformed != null) {
            Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onActionPerformed.positive();
                }
            });
            Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onActionPerformed.negative();
                }
            });
        }
        return this;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public interface OnActionPerformed {
        void positive();

        void negative();

    }
}
