package com.skeleton.retrofit2;




import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rest Client
 */
public class RestClient {

    public static Retrofit googleWebServices = null;
    public static Retrofit retrofit = null;

    /**
     *
     * @return
     */
    public static ApiInterface getApiInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.getBaseURL())
                    //.baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }




    public static ApiInterface getGoogleApiInterface() {
        if (googleWebServices == null) {

            try {
                googleWebServices = new Retrofit.Builder()
                        .baseUrl(Config.getGoogleUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return googleWebServices.create(ApiInterface.class);
    }



    /**
     *
     * @return
     */
    public static Retrofit getRetrofitBuilder() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.getBaseURL())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofit;
    }

    /**
     *
     * @return
     */
    private static OkHttpClient.Builder httpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient;
    }


}
