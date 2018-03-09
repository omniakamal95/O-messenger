package com.example.noso.myapplication.Interfaces;

import com.example.noso.myapplication.beans.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by abdelrahmantarek on 3/6/18.
 */

public interface UsersClient {

    @POST("users/signup/")
    Call<Users> signup(@Body Users users);

    @POST("users/login/")
    Call<Users> login(@Body Users users);

    @PUT("users/logout/")
    Call<String> logout(@Header("x-auth") String xAuth);
}
