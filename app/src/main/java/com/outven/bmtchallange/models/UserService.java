package com.outven.bmtchallange.models;

import com.outven.bmtchallange.models.forgot.ForgotResponse;
import com.outven.bmtchallange.models.login.Response.LoginDataResponse;
import com.outven.bmtchallange.models.profile.UpdateProfileResponse;
import com.outven.bmtchallange.models.register.Response.UserResponse;
import com.outven.bmtchallange.models.report.response.ReportResponse;
import com.outven.bmtchallange.models.upload.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {
    @FormUrlEncoded
    @POST("api/user/register/")
    Call<UserResponse> saveUser(
            @Field("nisn")  String nisn,
            @Field("password")  String password,
            @Field("name") String name,
            @Field("gender") int gender,
            @Field("birth_date") String birth_date,
            @Field("phone_number") String phone_number,
            @Field("school_class") String school_class
    );

    @FormUrlEncoded
    @POST("api/user/login/")
    Call<LoginDataResponse> userLogin(
            @Field("nisn") String nisn,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/user/profile/")
    Call<UpdateProfileResponse> userUpdateProfile(
            @Field("nisn") String nisn,
            @Field("name") String name,
            @Field("gender") int gender,
            @Field("birth_date") String birth_date,
            @Field("phone_number") String phone_number,
            @Field("school_class") String school_class
    );

    @FormUrlEncoded
    @POST("api/user/forgot/")
    Call<ForgotResponse> userChangePassword(
            @Field("nisn") String nisn,
            @Field("password") String password,
            @Field("newpassword") String newpassword
    );

    @FormUrlEncoded
    @POST("api/report/")
    Call<ReportResponse> userReport(@Field("nisn") String nisn);

    @Multipart
    @POST("api/report/add/")
    Call<UploadResponse> userUpload(
            @Part("report_id") RequestBody report_id,
            @Part MultipartBody.Part image,
            @Part("category") RequestBody category,
            @Part("status") RequestBody status
    );
}