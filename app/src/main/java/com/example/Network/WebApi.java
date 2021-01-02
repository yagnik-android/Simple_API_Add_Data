package com.example.Network;

import com.example.Response.AllUserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebApi {
    @FormUrlEncoded
    @POST("YOUR API PATH")
    Call<AllUserResponse> register(
        @Field("name") String name,
        @Field("email") String email,
        @Field("mobile_no") String mobile_no,
        @Field("password") String password
    );

}
