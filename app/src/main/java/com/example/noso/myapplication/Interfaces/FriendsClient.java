package com.example.noso.myapplication.Interfaces;

import com.example.noso.myapplication.beans.UserId;
import com.example.noso.myapplication.beans.UserName;
import com.example.noso.myapplication.beans.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by abdelrahmantarek on 3/7/18.
 */

public interface FriendsClient {

    @GET("/friends/")
    Call<List<Users>> friends(@Header("x-auth") String xAuth);

    @GET("/friends/requests/")
    Call<List<Users>> requests(@Header("x-auth") String xAuth);

    @PUT("friends/approveFriend/")
    Call<Users> approveFriend(@Header("x-auth") String xAuth, @Body UserId userId);

    @PUT("friends/addFriend")
    Call<Users> addFriend(@Header("x-auth") String xAuth, @Body UserId userId);

    @DELETE("friends/rejectFriend")
    Call<Users> rejectFriend(@Header("x-auth") String xAuth, @Body UserId userId);

    @POST("/friends/search")
    Call<List<Users>> search(@Header("x-auth") String xAuth, @Body UserName name);

    @DELETE("/friends/removeFriend")
    Call<Users> removeFriend(@Header("x-auth") String xAuth, @Body UserId userId);

}
