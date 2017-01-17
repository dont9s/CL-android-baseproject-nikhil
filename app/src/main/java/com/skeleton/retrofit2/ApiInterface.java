package com.skeleton.retrofit2;


import com.skeleton.model.MovieResponse;

import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ApiInterface
 */
public interface ApiInterface {


    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey , @Query("page") int page , @Query("sort_by") String sortBy);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @Multipart
    @POST("/fileUpload.php")
    Call<Void> uploadImage(@PartMap Map<String, RequestBody> map);

//    @FormUrlEncoded
//    @POST("/api/customer/loginViaFacebook")
   //Call<RegisterData> loginViaFb(@FieldMap Map<String, String> map);
//
//
//    @GET("/api/customer/getVehicleModels")
//    Call<VehicleModelData> getVehicleModels();
//
//    @FormUrlEncoded
//    @PUT("/api/customer/getVehicleModelsDetails")
//    Call<VehicleModelDetailsData> getVehicleModelDetails(@Field("vehicleModelID") String vehicleModelID);
//
//    @Multipart
//    @POST("/api/customer/register")
//    Call<RegisterData> register(@PartMap Map<String, RequestBody> map);
//
//    @FormUrlEncoded
//    @PUT("/api/customer/verifyOTP")
//    Call<SimpleMsgData> verifyOtp(@Header("authorization") String authorization, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("/api/customer/accessTokenLogin")
//    Call<RegisterData> accessTokenLogin(@Header("authorization") String authorization, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("/api/customer/login")
//    Call<RegisterData> login(@FieldMap Map<String, String> map);
//
//    @PUT("/api/customer/logout")
//    Call<SimpleMsgData> logout(@Header("authorization") String authorization);

}
