package com.outven.bmtchallange.models;

import com.outven.bmtchallange.models.forgot.ForgotResponse;
import com.outven.bmtchallange.models.login.Response.LoginDataResponse;
import com.outven.bmtchallange.models.register.Response.UserResponse;
import com.outven.bmtchallange.models.report.response.ReportResponse;
import com.outven.bmtchallange.models.upload.UploadRequest;
import com.outven.bmtchallange.models.upload.UploadResponse;

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

    @FormUrlEncoded
    @POST("api/user/forgot/")
    Call<ForgotResponse> userChangePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("newpassword") String newpassword
    );

    @FormUrlEncoded
    @POST("api/report/")
    Call<ReportResponse> userReport(@Field("email") String email);

    @POST("api/report/add/")
    Call<UploadResponse> userUpload(@Body UploadRequest uploadRequest);


    // GET Code
    @GET("api/user/forgot")
    Call<List<ForgotResponse>> getForgotResult();

    @GET("api/user/login")
    Call<List<UserResponse>> getAllUsers();

    @GET("api/report/")
    Call<List<ReportResponse>> getAllReportUser();

    @GET("api/report/add/")
    Call<List<UploadResponse>> getUserUploadResponse();
}