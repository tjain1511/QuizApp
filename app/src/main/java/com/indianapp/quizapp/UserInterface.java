package com.indianapp.quizapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInterface {

    @POST("/signup")
    Call<ResponseBody> executeSignup (
            @Body HashMap<String, String> map

    );


    @POST("/login")
    Call<ResponseBody> executeLogin(@Body HashMap<String,String> hashMap);



    @GET("/api.php")
    Call<JsonObject> getques(
            @Query("amount") String amount,
            @Query("category") String category,
            @Query("type") String type
    );
    @POST("/game")
    Call<ResponseBody> sendscor (
            @Header("auth-token") String tok,
            @Body HashMap<String, String> map
    );
    @GET("/history")
    Call<JsonArray> getHist(
            @Header("auth-token") String tok
    );
    @GET("/leaderboard")
    Call<JsonArray> getLead(

    );

    @POST("/user")
    Call<ResponseBody> sendData(
            @Header("auth-token") String tok,
            @Body HashMap<String,Integer> code
    );

    @GET("/user")
    Call<JsonObject> getde(
            @Header("auth-token") String tok
    );

}
