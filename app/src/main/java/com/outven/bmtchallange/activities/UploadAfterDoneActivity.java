package com.outven.bmtchallange.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.LoadingDialog;
import com.outven.bmtchallange.helper.SessionManager;
import com.outven.bmtchallange.models.upload.UploadResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    int doneUploadTracker = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_after_done);
        sessionManager = new SessionManager(UploadAfterDoneActivity.this);
        // Upload Code here

        imgUploadAfterDone = findViewById(R.id.imgUploadAfterDone);
        btnUploadAfterDone = findViewById(R.id.btnUploadAfterDone);

        imgUploadAfterDone.setImageURI(Config.getFileAfter());

        btnUploadAfterDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                doUpload();
            }
        });

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
        loadingToUpload();
    }

    private void loadingToUpload(){
        LoadingDialog loadingDialog = new LoadingDialog(UploadAfterDoneActivity.this, "Sedang mengupload...");
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (doneUploadTracker == 2) {
                    loadingDialog.dismissDialog();
                    if (Objects.requireNonNull(sessionManager.getUserDetail().get(Config.USER_REPORT_ENTRY)).equalsIgnoreCase("2") &&
                            Objects.requireNonNull(sessionManager.getUserDetail().get(Config.USER_DAY)).equalsIgnoreCase("21") &&
                            Config.isPoint() ){
                        moveToDashboard();
                    } else {
                        moveToNextPage(UploadAfterDoneActivity.this);
                    }
                }
            }
        },10000);
    }

    private void userUpload(RequestBody report_id, MultipartBody.Part partImageBefore, RequestBody category, RequestBody status) {
        Call<UploadResponse> userUpload = ApiClient.getUserService().userUpload(report_id, partImageBefore, category, status);
        userUpload.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(@NotNull Call<UploadResponse> call, @NotNull Response<UploadResponse> response) {
                try {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                        Toast.makeText(getApplicationContext(),""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Config.setPoint(true);
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal upload, "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Config.setPoint(false);
                    }
                    doneUploadTracker++;
                } catch (Exception e){
                    Toast.makeText(UploadAfterDoneActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NotNull Call<UploadResponse> call, @NotNull Throwable t) {
                try {
                    Toast.makeText(UploadAfterDoneActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(UploadAfterDoneActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void moveToDashboard(){
        Intent intent = new Intent(UploadAfterDoneActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        doneUploadTracker = 0;
        finish();
    }

    private void moveToNextPage(Context context){
        Intent intent = new Intent(context, AchivmentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private long mLastClickTime = 0;
}