package com.outven.bmtchallange.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/user/register/")
    Call<UserResponse> saveUser(@Body UserRequest userRequest);

    @POST("api/user/login/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("api/report/")
    Call<ReportResponse> userReport(@Body ReportRequest reportRequest);

    @POST("api/report/add/")
    Call<UploadResponse> userUpload(@Body UploadRequest uploadRequest);

    @GET("api/user/login")
    Call<List<UserResponse>> getAllUsers();

    @GET("api/report/")
    Call<List<ReportResponse>> getAllReportUser();

    @GET("api/report/add/")
    Call<List<UploadResponse>> getUserUploadResponse();
}