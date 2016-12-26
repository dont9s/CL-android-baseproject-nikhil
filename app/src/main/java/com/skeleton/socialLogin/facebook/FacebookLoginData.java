package com.skeleton.socialLogin.facebook;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class FacebookLoginData implements Serializable {

    public static Parcelable.Creator<FacebookLoginData> CREATOR = new Parcelable.Creator<FacebookLoginData>() {
        @Override
        public FacebookLoginData createFromParcel(Parcel parcel) {
            return new FacebookLoginData(parcel);
        }

        @Override
        public FacebookLoginData[] newArray(int i) {
            return new FacebookLoginData[i];
        }
    };

    private String email;
    private String first_name;
    private String last_name;


    private String pic_big;

    private String access_token;

    private String socialUserID;


    public FacebookLoginData(Parcel source) {

        email = source.readString();
        first_name = source.readString();
        last_name = source.readString();
        pic_big = source.readString();
        access_token = source.readString();

        socialUserID = source.readString();

    }

    public FacebookLoginData() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }


    public String getSocialUserID() {
        return socialUserID;
    }

    public void setSocialUserID(String socialUserID) {
        this.socialUserID = socialUserID;
    }


}
