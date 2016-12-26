package com.skeleton.socialLogin.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.skeleton.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class FacebookLogin {

    /*


      =========================== How to use  ============================
    =============Declare these variables in your Activity /fragment===================
      private CallbackManager callbackManager;
      FacebookLogin socialLogin;

     ===========Add this in your Activity /fragment=======================
      @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
     callbackManager.onActivityResult(requestCode, resultCode, data);
      }

     ===========call this in onCreate of Activity/Fragment ===================

    private void initCallbackManager() {

        socialLogin = new FacebookLogin(getActivity());

        // Just pass the id of your button, for which to handle the FACEBOOK LOGIN
        callbackManager = socialLogin.loginViaFacebook();
    }

    */


    //=========================== Private variable ============================
    public static final int FACEBOOK_LOGIN = 0x6;

    private Activity activity;
    private Fragment fragment;
    private FacebookLoginData loginData;
    private CallbackManager callbackManager;
    private android.app.Fragment appFragment;

    private String[] PERMISSION_LIST = {"email, public_profile"};

    private String TAG=getClass().getSimpleName();

    //=========================== Constructors ============================
    public FacebookLogin(Activity activity) {
        this.activity = activity;
    }

    public FacebookLogin(Fragment fragment) {
        this.fragment = fragment;
    }

    public FacebookLogin(android.app.Fragment appFragment) {
        this.appFragment = appFragment;
    }


    /**
     * Method to login the user through facebook
     */
    public CallbackManager loginViaFacebook() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        Log.v(TAG+"======ACCESS TOKEN", loginResult.getAccessToken().getToken());

                        loginData = new FacebookLoginData();
                        loginData.setAccess_token(loginResult.getAccessToken().getToken());

                        requestFacebookUserData(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.v(TAG+"======CANCEL", "CANCEL");
                        loginData = new FacebookLoginData();
                    }


                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.v(TAG+"======FACEBOOK ERROR", "ERROR" + exception);
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }

                        //  CustomDialog.showAlertDialog(activity, "Error", "Please try other alternatives.", RequestCodes.DEFAULT);
                    }
                });


        return callbackManager;
    }


    public void doLogin() {
        if (activity != null)
            LoginManager.getInstance().logInWithReadPermissions(activity,
                    Arrays.asList(PERMISSION_LIST));
        else if (fragment != null)
            LoginManager.getInstance().logInWithReadPermissions(fragment,
                    Arrays.asList(PERMISSION_LIST));
        else if (appFragment != null)
            LoginManager.getInstance().logInWithReadPermissions(appFragment,
                    Arrays.asList(PERMISSION_LIST));
    }

    /**
     * Method to request the UserData from Facebook
     *
     * @param token
     */
    public void requestFacebookUserData(final AccessToken token) {

        GraphRequest request = GraphRequest.newMeRequest(
                token, new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code

                        if (object != null) {
                            try {

                                Log.v("GRAPH RESPONSE", response.toString());
                                Log.v("JSON OBJECT", object.toString());

                                loginData.setAccess_token(token.getToken());
                                String key;

                                key = "id";
                                loginData.setSocialUserID(getValue(key, object));


                                key = "name";
                                String name = getValue(key, object);

                                key = "email";
                                loginData.setEmail(getValue(key, object));

                                key = "first_name";
                                loginData.setFirst_name(getValue(key, object));

                                key = "last_name";
                                loginData.setLast_name(getValue(key, object));


                                returnFacebookUserData();
                            } catch (JSONException jEx) {
                                if (fragment == null)
                                    Toast.makeText(activity, activity.getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(fragment.getActivity(), fragment.getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email, first_name, last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Method to safely set the the values to the Fields
     *
     * @param key
     * @param jObj
     * @return
     * @throws JSONException
     */
    private String getValue(String key, JSONObject jObj) throws JSONException {

        if (jObj.has(key))
            return jObj.getString(key);

        return "";
    }

    /**
     * Return the User data to the User
     */
    private void returnFacebookUserData() {

        String pic = null;

        try {
            pic = Profile.getCurrentProfile().getProfilePictureUri(400, 400).toString();

            Log.e(TAG+"======fb pic url", "==" + pic);

        } catch (Exception e) {
            e.printStackTrace();

            pic = "https://graph.facebook.com/" + loginData.getSocialUserID() + "/picture?type=large";
        }

        loginData.setPic_big(pic);

        LoginManager.getInstance().logOut();

        if (activity != null && activity instanceof OnFacebookLoginListener)
            ((OnFacebookLoginListener) activity).onSocialLogin(FACEBOOK_LOGIN, loginData);
        else if (fragment != null && fragment instanceof OnFacebookLoginListener)
            ((OnFacebookLoginListener) fragment).onSocialLogin(FACEBOOK_LOGIN, loginData);
        else if (appFragment != null && appFragment instanceof OnFacebookLoginListener)
            ((OnFacebookLoginListener) appFragment).onSocialLogin(FACEBOOK_LOGIN, loginData);

    }
}
