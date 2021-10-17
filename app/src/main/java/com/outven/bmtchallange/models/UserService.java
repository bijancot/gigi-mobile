package com.outven.bmtchallange.models;

import com.outven.bmtchallange.models.Forgot.ForgotResponse;
import com.outven.bmtchallange.models.login.Response.LoginDataResponse;
import com.outven.bmtchallange.models.register.Response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("api/user/register/")
    Call<UserResponse> saveUser(
            @Field("email")  String email,
            @Field("password")  String password,
            @Field("name") String name,
            @Field("gender") int gender,
            @Field("birth_date") String birth_date,
            @Field("school_name") String school_name,
            @Field("phone_number") String phone_number,
            @Field("school_class") String school_class
    );

    @FormUrlEncoded
    @POST("api/user/login/")
    Call<LoginDataResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("api/report/")
    Call<ReportResponse> userReport(@Body ReportRequest reportRequest);

    @POST("api/report/add/")
    Call<UploadResponse> userUpload(@Body UploadRequest uploadRequest);

    @FormUrlEncoded
    @POST("api/user/forgot/")
    Call<ForgotResponse> userChangePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("newpassword") String newpassword
    );

    @GET("api/user/forgot")
    Call<List<ForgotResponse>> getForgotResult();

    @GET("api/user/login")
    Call<List<UserResponse>> getAllUsers();

    @GET("api/report/")
    Call<List<ReportResponse>> getAllReportUser();

    @GET("api/report/add/")
    Call<List<UploadResponse>> getUserUploadResponse();
}