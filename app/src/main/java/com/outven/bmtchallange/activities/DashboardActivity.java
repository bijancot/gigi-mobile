package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.activities.adapter.MyAdapter;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.LoadingDialog;
import com.outven.bmtchallange.helper.SessionManager;
import com.outven.bmtchallange.models.report.response.Report;
import com.outven.bmtchallange.models.report.response.ReportResponse;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    String videoPath, userReportStatus, userTime;
    String userTrackerDay, userEntry;

    RelativeLayout rlDashboard;
    SwipeRefreshLayout refreshLayout;

    TextView txtUsername, txtTier, tittleTracker;
    CardView cvProfile;
    ImageButton ibFullScreen;
    VideoView vvTutorial;
    RecyclerView rvTracker;
    ImageView ivLogoUser, btnOption;

    SessionManager sessionManager;
    LoadingDialog loadingDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(DashboardActivity.this);
        try {
            loadingDialog = new LoadingDialog(DashboardActivity.this, "Tunggu sebentar...");
            loadingDialog.startLoadingDialog();
            userReport(sessionManager.getUserDetail().get(Config.USER_NISN));

            refreshLayout = findViewById(R.id.refreshLayout);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    moveToNextPage(DashboardActivity.this,DashboardActivity.class,true);
                    refreshLayout.setRefreshing(false);
                }
            });
        } catch (Exception e){
            Log.e("Error, ", e.getLocalizedMessage());
            sessionManager.loggoutSession();
            Toast.makeText(DashboardActivity.this,"Terjadi kesalahan pada server", Toast.LENGTH_LONG).show();
            moveToNextPage(DashboardActivity.this,LoginActivity.class,true);
        }

        //Hiden Bar
        HidenBar.WindowFlag(this, getWindow());
    }

    @SuppressLint("SetTextI18n")
    private void refreshDashboard(){
        //Find id
        ivLogoUser = findViewById(R.id.ivLogoUser);
        btnOption = findViewById(R.id.btnOption);
        cvProfile = findViewById(R.id.cvProfile);
        txtTier = findViewById(R.id.txtTier);
        txtUsername = findViewById(R.id.txtUsername);
        tittleTracker = findViewById(R.id.tittleTracker);
        vvTutorial = findViewById(R.id.vvTutorial);
        ibFullScreen = findViewById(R.id.ibFullScreen);
        rvTracker = findViewById(R.id.rvTracker);
        rlDashboard = findViewById(R.id.rlDashboard);

//        int trackerDay = 12;
//        int entry = 0;
//        userTime = "night";
//        Config.setPoint(true);

        txtUsername.setText("Halo "+sessionManager.getUserDetail().get(Config.USER_NAME)+"!");

        userTrackerDay = sessionManager.getUserDetail().get(Config.USER_DAY);
        userEntry = sessionManager.getUserDetail().get(Config.USER_REPORT_ENTRY);

        int trackerDay = Integer.parseInt(userTrackerDay);
        int entry = Integer.parseInt(userEntry);
        userTime = sessionManager.getUserDetail().get("report_time");
        userReportStatus = sessionManager.getUserDetail().get("report_status");

        try {
            curTimeCheck(userTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PointTracker(
            entry,
            trackerDay
        );

        if (Config.isPoint() && entry == 4 && Objects.requireNonNull(sessionManager.getUserDetail().get(Config.USER_REPORT_STATUS)).equalsIgnoreCase("ongoing")){
            showPointDialog();
        }

        //Video setter
        MediaController mediaController = new MediaController(this);

        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videodashboard;
        Uri uri = Uri.parse(videoPath);
        vvTutorial.setVideoURI(uri);
        vvTutorial.setMediaController(mediaController);
        vvTutorial.seekTo(10000);
        mediaController.setAnchorView(vvTutorial);

        //Grid Adapter and clickLIstener
        MyAdapter myAdapter = new MyAdapter(
                DashboardActivity.this,
                getResources().getStringArray(R.array.dayListTracker),
                trackerDay,
                userReportStatus,
                userTime,
                entry

        );

        rvTracker.setAdapter(myAdapter);

        btnOption.setOnClickListener(this);
        ibFullScreen.setOnClickListener(this);
    }

    private void showPointDialog() {
        Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_point_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Config.setPoint(false);
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void PointTracker(int Entry, int Day) {
        int Tier;
        if (Entry == 4){
            Tier = Day*100;
        } else {
            Tier = (Day*100) - 100;
        }
        txtTier.setText(Tier + " Poin");
    }


    //    Check timer day or night
    private void curTimeCheck(String time){
        if (Objects.equals(time,Config.TIME_NIGHT)){
            nightTheme();
        } else {
            dayTheme();
        }
    }

    //Night Theme
    private void nightTheme() {
        rlDashboard.setBackgroundColor(getResources().getColor(R.color.textPrimary));
        txtUsername.setTextColor(getResources().getColor(R.color.white));
        txtTier.setTextColor(getResources().getColor(R.color.text));
    }
    //Day Theme
    private void dayTheme() {
        rlDashboard.setBackgroundColor(getResources().getColor(R.color.nowYellow));
        txtUsername.setTextColor(getResources().getColor(R.color.text));
        txtTier.setTextColor(getResources().getColor(R.color.text));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnOption:
                //just do onlclick
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                moveToNextPage(DashboardActivity.this, ProfilActivity.class,false);
                break;
            case R.id.ibFullScreen:
                //just do onlclick
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Config.setVidePath(videoPath);
                moveToNextPage(DashboardActivity.this, FullScreenActivity.class,false);
                break;
        }
    }

    private void userReport(String email) {
        Call<ReportResponse> userReport = ApiClient.getUserService().userReport(email);
        userReport.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportResponse> call, @NotNull Response<ReportResponse> response) {
                try {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                        Report report = response.body().getData().getReport();
                        sessionManager.ReportSession(report);
                        refreshDashboard();
                        loadingDialog.dismissDialog();
                    } else {
                        Toast.makeText(DashboardActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(DashboardActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReportResponse> call, @NotNull Throwable t) {
                try {
                    Toast.makeText(DashboardActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(DashboardActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveToNextPage(Context context, Class<? extends Activity> activityClass, boolean setFlags){
        Intent intent = new Intent(context, activityClass);
        if (setFlags){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

    private long mLastClickTime = 0;
}
