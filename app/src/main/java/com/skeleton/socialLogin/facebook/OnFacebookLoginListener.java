package com.skeleton.socialLogin.facebook;


public interface OnFacebookLoginListener {

    /**
     * Override this method to get Login Data
     *
     * @param type
     * @param loginData
     */
    public void onSocialLogin(int type, FacebookLoginData loginData);
}
