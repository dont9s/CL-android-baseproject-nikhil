package com.skeleton.database;


import com.skeleton.constant.AppConstant;
import com.skeleton.constant.PaperDbConstant;

import java.util.Date;
import java.util.Map;

import io.paperdb.Paper;

/**
 * Created by kashish nalwa 12/12/16.
 * CommonData to be used for handling any user data be its access token, device token and userDetails.
 * Static fields related to user data in Common data to come in capitalized case.
 */
public class CommonData implements PaperDbConstant {

    public static String ACCESS_TOKEN = null;
    public static String DEVICE_TOKEN = null;
    public static Map<String, String>  PUSH_DATA=null;

    public static Map<String, String> getPushData() {
        return PUSH_DATA;
    }

    public static void setPushData(Map<String, String> pushData) {
        PUSH_DATA = pushData;
    }


    public static String getAccessToken() {
        if (ACCESS_TOKEN == null) {
            ACCESS_TOKEN = Paper.book().read(PAPER_ACCESS_TOKEN, "");
        }
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        Paper.book().write(ACCESS_TOKEN, accessToken);
        ACCESS_TOKEN = accessToken;
    }

    public static String getDeviceToken() {
        if (DEVICE_TOKEN == null) {
            DEVICE_TOKEN = Paper.book().read(PAPER_DEVICE_TOKEN, "");
        }
        return DEVICE_TOKEN;
    }


    public static void setDeviceToken(String deviceToken) {
        Paper.book().write(PAPER_DEVICE_TOKEN, deviceToken);
        DEVICE_TOKEN = deviceToken;
    }

    public static void deletePaper() {
        ACCESS_TOKEN = null;
        DEVICE_TOKEN = null;
        Paper.book().destroy();
    }
}
