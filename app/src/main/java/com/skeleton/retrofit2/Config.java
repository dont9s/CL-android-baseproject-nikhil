package com.skeleton.retrofit2;


/**
 * Developer: Kashish nalwa
 * Dated: 2/29/16.
 */
public class Config {
    static String GCM_PROJECT_NUMBER = "";
    static String BASE_URL = "";
    static String FLURRY_KEY = "";
    static String GOOGLE_URL = "https://maps.googleapis.com/maps/api/";
    static AppMode appMode = AppMode.DEV;

    static public String getBaseURL() {

        init(appMode);
        return BASE_URL;
    }

    static public String getGoogleUrl() {
        return GOOGLE_URL;
    }

    static public String getFlurryKey() {

        init(appMode);

        return FLURRY_KEY;
    }

    static public String getGCMProjectNumber() {

        init(appMode);

        return GCM_PROJECT_NUMBER;
    }


    /**
     * Initialize all the variable in this method
     *
     * @param appMode
     */
    public static void init(AppMode appMode) {

        switch (appMode) {
            case DEV:

                BASE_URL = "http://35.160.24.66:3001/";
                FLURRY_KEY = "MNZJSQ9YV376F3NM39VZ";
                GCM_PROJECT_NUMBER = "543039069979";
                break;

            case TEST:

                BASE_URL = "http://35.160.24.66:3000/";
                FLURRY_KEY = "MNZJSQ9YV376F3NM39VZ";
                GCM_PROJECT_NUMBER = "543039069979";
                break;
            case CLIENT:

                BASE_URL = "http://54.187.167.103:3002/";
                FLURRY_KEY = "MNZJSQ9YV376F3NM39VZ";
                GCM_PROJECT_NUMBER = "543039069979";
                break;
            case LIVE:

                BASE_URL = "http://54.172.84.88:3003";
                FLURRY_KEY = "MNZJSQ9YV376F3NM39VZ";
                GCM_PROJECT_NUMBER = "563232976573";
                break;

        }


    }

    public enum AppMode {
        DEV, TEST, LIVE, CLIENT
    }

    //Server Key for GCM     AIzaSyAAnVPj9uQGrvaQcX2tLMcIUT9Bbs9HVqg
//    static String GCM_PROJECT_NUMBER = "";
//    static String BASE_URL = "";
//    static AppMode appMode = AppMode.DEV;
//    static String GOOGLE_URL = "";
//
//    static public String getBaseURL() {
//        init(appMode);
//        return BASE_URL;
//    }
//
//    static public String getGoogleUrl(){
//        init(appMode);
//        return GOOGLE_URL;
//    }
//
//
//    static public String getGCMProjectNumber() {
//        init(appMode);
//        return GCM_PROJECT_NUMBER;
//    }
//
//
//    /**
//     * Initialize all the variable in this method
//     *
//     * @param appMode
//     */
//    public static void init(AppMode appMode) {
//
//        switch (appMode) {
//            case DEV:
//                BASE_URL = "http://54.191.139.45:8000/";
//                GCM_PROJECT_NUMBER = "431122027989";
//                GOOGLE_URL = "https://maps.googleapis.com";
//                break;
//
//            case TEST:
//                BASE_URL = "http://54.191.139.45:8001/";
//                GCM_PROJECT_NUMBER = "431122027989";
//                GOOGLE_URL = "http://maps.googleapis.com";
//                break;
//
//            case LIVE:
//                BASE_URL = "http://54.191.139.45:8002/";
//                GCM_PROJECT_NUMBER = "431122027989";
//                GOOGLE_URL = "http://maps.googleapis.com";
//                break;
//        }
//
//    }
//
//    public enum AppMode {
//        DEV, TEST, LIVE
//    }

}