package com.outven.bmtchallange;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/")
    Call<UserResponse> saveUser(@Body UserRequest userRequest);
}
