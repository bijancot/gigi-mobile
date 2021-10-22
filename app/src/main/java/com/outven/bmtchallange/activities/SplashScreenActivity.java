package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;
import com.outven.bmtchallange.models.report.response.ReportResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    Button btnSplashMulai;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(SplashScreenActivity.this);
        btnSplashMulai = findViewById(R.id.btnSplashMulai);

        String today = (String) DateFormat.format("yyyy-MM-dd", new Date());

        Log.e("failure ----------------" , "TODAY : "+today);
        Log.e("failure ----------------" , "Login : "+ sessionManager.isLoggedIn()+" , haveOpenAppToday : "+ sessionManager.haveOpenAppToday(today) + ", haveReportToday : "+ sessionManager.haveReportToday());
        btnSplashMulai.setOnClickListener(view -> {

//            sessionManager.ReportedToday(false);
//            sessionManager.changeDateLastDay("");
//            sessionManager.changeStatusLoggedIn(false);

            if (sessionManager.isLoggedIn()){
                if (!sessionManager.haveOpenAppToday(today) && !sessionManager.haveReportToday()){
                    createReportUser();
                    Toast.makeText(this,"Reported", Toast.LENGTH_LONG).show();
                }
            }
            moveToDashboard();
        });

        HidenBar.WindowFlag(SplashScreenActivity.this, getWindow());

//        Thread timer = new Thread() {
//            public void run() {
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//
//                }
//            }
//        };
//        timer.start();
    }

    private void createReportUser() {
        userReport(sessionManager.getUserDetail().get("email"));
    }

    private void userReport(String email) {
        Call<ReportResponse> userReport = ApiClient.getUserService().userReport(email);
        userReport.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportResponse> call, @NotNull Response<ReportResponse> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
//                    sessionManager.ReportSession(response.body());
                    try {
                        sessionManager.ReportedToday(true);
                        Toast.makeText(SplashScreenActivity.this,""+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Toast.makeText(SplashScreenActivity.this,""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReportResponse> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage()+ " Report Trouble", Toast.LENGTH_SHORT).show();
                Log.e("failure " , t.getLocalizedMessage());
            }
        });
    }

    public void moveToDashboard(){
        Intent m = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivityForResult(m,0);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }
}