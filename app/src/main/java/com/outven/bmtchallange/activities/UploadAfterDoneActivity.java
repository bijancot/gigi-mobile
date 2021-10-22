package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;
import com.outven.bmtchallange.models.upload.UploadResponse;

import org.jetbrains.annotations.NotNull;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadAfterDoneActivity extends AppCompatActivity {

    Button btnUploadAfterDone;
    ImageView imgUploadAfterDone;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_after_done);
        sessionManager = new SessionManager(UploadAfterDoneActivity.this);
        // Upload Code here

        imgUploadAfterDone = findViewById(R.id.imgUploadAfterDone);
        btnUploadAfterDone = findViewById(R.id.btnUploadAfterDone);

        imgUploadAfterDone.setImageURI(Config.getFileAfter());

        btnUploadAfterDone.setOnClickListener(view -> doUpload());

        HidenBar.WindowFlag(this, getWindow());
    }

    private void doUpload() {

        String id, category, status;

        for (int i=0;i<Config.files.length;i++){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), Config.files[i]);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", Config.files[i].getName(), requestBody);

            id = sessionManager.getUserDetail().get("report_id");
            category = Config.listUpload[i][0];
            status = Config.listUpload[i][1];

            RequestBody rid = RequestBody.create(MediaType.parse("text/plain"),id);
            RequestBody rcategory = RequestBody.create(MediaType.parse("text/plain"),category);
            RequestBody rstatus = RequestBody.create(MediaType.parse("text/plain"),status);

            userUpload(rid, partImage, rcategory, rstatus);
        }

//        RequestBody requestBodyBefore = RequestBody.create(MediaType.parse("image/*"), imageFileBefore);
//        RequestBody requestBodyAfter = RequestBody.create(MediaType.parse("image/*"), imageFileAfter);
//
//        MultipartBody.Part partImageBefore = MultipartBody.Part.createFormData("image", imageFileBefore.getName(), requestBodyBefore);
//        MultipartBody.Part partImageAfter = MultipartBody.Part.createFormData("image", imageFileBefore.getName(), requestBodyAfter);
//
//        RequestBody report_id = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getUserDetail().get("report_id"));
//        RequestBody category = RequestBody.create(MediaType.parse("text/plain"),"day");
//        RequestBody status = RequestBody.create(MediaType.parse("text/plain"),"before");
//
//        userUpload(report_id, partImageBefore, category, status);
    }

    private void userUpload(RequestBody report_id, MultipartBody.Part partImageBefore, RequestBody category, RequestBody status) {
        Call<UploadResponse> userUpload = ApiClient.getUserService().userUpload(report_id, partImageBefore, category, status);
        userUpload.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(@NotNull Call<UploadResponse> call, @NotNull Response<UploadResponse> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    try {
                        moveToDashboard();
                        Toast.makeText(getApplicationContext(),""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NotNull Call<UploadResponse> call, @NotNull Throwable t) {
                Log.d("RETRO","onTrouble : "+t.getMessage());
            }
        });
    }


    public void moveToDashboard(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(UploadAfterDoneActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        finish();
    }
    private long mLastClickTime = 0;
}