package com.skeleton.retrofit2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import com.google.gson.JsonSyntaxException;
import com.skeleton.activity.SplashActivity;
import com.skeleton.database.CommonData;
import com.skeleton.plugin.Log;
import com.skeleton.util.dialog.SingleBtnDialog;
import com.skeleton.util.loadingBox.LoadingBox;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Custom Retrofit ResponseResolver
 *
 * @param <T>
 */
public abstract class ResponseResolver<T> implements Callback<T> {
    private WeakReference<Activity> weakActivity = null;
    private Boolean showLoading = false;
    private Boolean showError = false;

    private final String NO_INTERNET_MESSAGE = "No internet connection. Please try again later.";
    private final String REMOTE_SERVER_FAILED_MESSAGE = "Application server could not respond. Please try later.";
    public final static String UNEXPECTED_ERROR_OCCURRED = "Something went wrong. Please try again later";
    private final static String PARSING_ERROR = "Parsing error";

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    /**
     * @param activity
     */
    public ResponseResolver(Activity activity) {
        this.weakActivity = new WeakReference<>(activity);
    }


    /**
     * @param activity
     * @param showLoading
     */
    public ResponseResolver(Activity activity, Boolean showLoading) {
        this.weakActivity = new WeakReference<>(activity);
        this.showLoading = showLoading;
        if (showLoading)
            LoadingBox.showOn(activity);
    }


    /**
     * @param activity
     * @param showLoading
     * @param showError
     */
    public ResponseResolver(Activity activity, Boolean showLoading, Boolean showError) {
        this.weakActivity = new WeakReference<>(activity);
        this.showLoading = showLoading;
        this.showError = showError;
        if (showLoading)
            LoadingBox.showOn(activity);

    }

    public abstract void success(T t);

    public abstract void failure(APIError error);

    @Override
    public void onResponse(Call<T> t, Response<T> tResponse) {
        LoadingBox.hide();
        if (tResponse.isSuccessful())
            success(tResponse.body());
        else
            fireError(ErrorUtils.parseError(tResponse));
    }

    @Override
    public void onFailure(Call<T> t, Throwable throwable) {
        LoadingBox.hide();
        fireError(new APIError(900, resolveNetworkError(throwable)));
    }


    /**
     * @param apiError
     */
    public void fireError(APIError apiError) {


        if (showError) {
            if (weakActivity.get() != null) {
                if (checkAuthorizationError(apiError)) {
                    return;
                }
            }

        }

        failure(apiError);
    }

    /**
     * @param apiError
     * @return
     */
    public Boolean checkAuthorizationError(APIError apiError) {

        if (apiError.getStatusCode() == 401) {
            /**
             * if authorization error than clear paper db and go back to splash screen on ok press
             * else show the error message
             */

        SingleBtnDialog.with(weakActivity.get()).setMessage(apiError.getMessage()).setCallback(new SingleBtnDialog.OnActionPerformed() {
            @Override
            public void positive() {
                CommonData.deletePaper();
                Intent intent = new Intent(weakActivity.get(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                weakActivity.get().startActivity(intent);
            }
        }).show();

//            CommonDialog.signleBtnDialog(weakActivity.get(), "", apiError.getMessage(), false, new CommonDialog.OnActionPerformed() {
//                @Override
//                public void positive() {
//                    CommonData.deletePaper();
//                    Intent intent = new Intent(weakActivity.get(), SplashActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                            Intent.FLAG_ACTIVITY_NEW_TASK);
//                    weakActivity.get().startActivity(intent);
//                }
//
//                @Override
//                public void negative() {
//
//                }
//            });
            return false;
        } else {
            if (showError) {

                SingleBtnDialog.with(weakActivity.get()).setMessage(apiError.getMessage()).show();
//                CommonDialog.signleBtnDialog(weakActivity.get(), "", apiError.getMessage(), false, new CommonDialog.OnActionPerformed() {
//                    @Override
//                    public void positive() {
//                    }
//
//                    @Override
//                    public void negative() {
//                    }
//                });
            }
        }

        return true;
    }

    /**
     * Method resolve network errors
     *
     * @param cause
     * @return
     */

    private String resolveNetworkError(Throwable cause) {
        Log.e("resolveNetworkError =", String.valueOf(cause.toString()));

        if (cause instanceof UnknownHostException)
            return NO_INTERNET_MESSAGE;
        else if (cause instanceof SocketTimeoutException)
            return REMOTE_SERVER_FAILED_MESSAGE;
        else if (cause instanceof ConnectException)
            return NO_INTERNET_MESSAGE;
        else if (cause instanceof JsonSyntaxException)
            return PARSING_ERROR;
        return UNEXPECTED_ERROR_OCCURRED;
    }

}
