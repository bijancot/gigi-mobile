package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.activities.adapter.MyAdapter;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    String videoPath, userReportStatus;
    int userTrackerDay;

    Date batas, curTime;
    LinearLayout llBorderName, llBorderTier;
    RelativeLayout rlDashboard;

    TextView txtUsername, txtTier, tittleTracker;
    CardView cvProfile;
    ImageButton ibFullScreen;
    VideoView vvTutorial;
    RecyclerView rvTracker;
    ImageView ivLogoUser;

    SessionManager sessionManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Find id
        ivLogoUser = findViewById(R.id.ivLogoUser);
        cvProfile = findViewById(R.id.cvProfile);
        txtTier = findViewById(R.id.txtTier);
        txtUsername = findViewById(R.id.txtUsername);
        tittleTracker = findViewById(R.id.tittleTracker);
        vvTutorial = findViewById(R.id.vvTutorial);
        ibFullScreen = findViewById(R.id.ibFullScreen);
        rvTracker = findViewById(R.id.rvTracker);

        try {
            curTimeCheck();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Cek user login
        sessionManager = new SessionManager(DashboardActivity.this);

        //set User detail Dashboard
        txtUsername.setText("Halo "+sessionManager.getUserDetail().get("name")+"!");
        txtTier.setText(sessionManager.getUserDetail().get("tier") + " Poin");

        userTrackerDay = Integer.parseInt(sessionManager.getUserDetail().get("tier"));
        userReportStatus = sessionManager.getUserDetail().get("report_status");

        //Video setter
        MediaController mediaController = new MediaController(this);
        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videodashboard;
        Uri uri = Uri.parse(videoPath);
        vvTutorial.setVideoURI(uri);
        vvTutorial.setMediaController(mediaController);
        vvTutorial.seekTo(10000);
        mediaController.setAnchorView(vvTutorial);
        Config.setVidePath(videoPath);

        //Grid Adapter and clickLIstener
        MyAdapter myAdapter = new MyAdapter(
                DashboardActivity.this,
                getResources().getStringArray(R.array.dayListTracker),
                userTrackerDay,
                userReportStatus
        );
        rvTracker.setAdapter(myAdapter);

        cvProfile.setOnClickListener(this);
        ibFullScreen.setOnClickListener(this);

        //Hiden Bar
        HidenBar.WindowFlag(this, getWindow());
    }

    //Check timer day or night
    private void curTimeCheck() throws ParseException {

        rlDashboard = findViewById(R.id.rlDashboard);

        String yourTime = "06:00 PM";
        String today = (String) android.text.format.DateFormat.format(
                "h:mm a", new java.util.Date());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat localtime = new SimpleDateFormat("h:mm a");
        batas = localtime.parse(yourTime);
        curTime = localtime.parse(today);
        if (curTime.after(batas)) {
            nightTheme();
        }
    }

    //Night Theme
    private void nightTheme() {
        rlDashboard.setBackgroundColor(getResources().getColor(R.color.textPrimary));
        txtUsername.setTextColor(getResources().getColor(R.color.white));
        txtTier.setTextColor(getResources().getColor(R.color.white));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cvProfile:
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

                moveToNextPage(DashboardActivity.this, FullScreenActivity.class,false);
                break;
        }
    }


    private void moveToNextPage(Context context, Class<? extends Activity> activityClass, boolean setFlags){
        Intent intent = new Intent(context, activityClass);
        if (setFlags){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }

    private long mLastClickTime = 0;
}
