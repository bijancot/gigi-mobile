package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
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

    static final String[] trackerDay = {
            "1","2","3","4","5","6","7",
            "8","9","10","11","12","13","14",
            "15","16","17","18","19","20","21"};
    String videoPath;
    String name,tier;
    Date batas;
    Date curTime;
    int userTrackerDay;

    TextView txtUsername, txtTier, txtTracker, tittleTracker;
    LinearLayout llBorderName, llBorderTier;
    GridView gvTracker;
    CardView cvProfile;
    RelativeLayout rlDashboard, btnTracker;
    ImageView ivTheme,ivTrackerDone;
    ImageButton ibFullScreen;
    VideoView vvTutorial;
    RecyclerView rvTracker;

    SessionManager sessionManager;
    Config config = new Config();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Find id
        cvProfile = findViewById(R.id.cvProfile);
        txtUsername = findViewById(R.id.txtUsername);
        txtTier = findViewById(R.id.txtTier);
        txtUsername = findViewById(R.id.txtUsername);
        tittleTracker = findViewById(R.id.tittleTracker);
        ivTheme = findViewById(R.id.ivTheme);
        vvTutorial = findViewById(R.id.vvTutorial);
        ibFullScreen = findViewById(R.id.ibFullScreen);
        rvTracker = findViewById(R.id.rvTracker);
//        gvTracker = findViewById(R.id.gvTracker);

        //cek curtime
        try {
            curTimeCheck();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Cek user login
        sessionManager = new SessionManager(DashboardActivity.this);
        if (!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        //get sharedPreference values into string
        name = sessionManager.getUserDetail().get("name");
        tier = sessionManager.getUserDetail().get("tier");
        userTrackerDay = Integer.parseInt(sessionManager.getUserDetail().get("tier"))-1;

        //set User detail Dashboard
        txtUsername.setText(name);
        txtTier.setText(tier);

        //Video setter
        MediaController mediaController = new MediaController(this);
        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videodashboard;
        Uri uri = Uri.parse(videoPath);
        vvTutorial.setVideoURI(uri);
        vvTutorial.setMediaController(mediaController);
        vvTutorial.seekTo(10000);
        mediaController.setAnchorView(vvTutorial);
        config.setVidePath(videoPath);
        ibFullScreen.setOnClickListener(this);

        //Grid Adapter and clickLIstener
        MyAdapter myAdapter = new MyAdapter(DashboardActivity.this,getResources().getStringArray(R.array.dayListTracker),userTrackerDay,videoPath);
        rvTracker.setAdapter(myAdapter);
        cvProfile.setOnClickListener(this);


        //Hiden Bar
        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }

    private void curTimeCheck() throws ParseException {

        rlDashboard = findViewById(R.id.rlDashboard);
        llBorderName = findViewById(R.id.llBorderName);
        llBorderTier = findViewById(R.id.llBorderTier);

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
        rlDashboard.setBackgroundColor(getResources().getColor(R.color.nightColor));
        llBorderName.setBackgroundResource(R.drawable.custom_border_night);
        llBorderTier.setBackgroundResource(R.drawable.custom_border_night);
        txtUsername.setTextColor(getResources().getColor(R.color.purple));
        txtTier.setTextColor(getResources().getColor(R.color.purple));
        tittleTracker.setTextColor(getResources().getColor(R.color.white));
        ivTheme.setImageResource(R.drawable.tiernight);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cvProfile:
                startActivity(new Intent(DashboardActivity.this, ProfilActivity.class));
                break;
            case R.id.ibFullScreen:
                Intent intent = new Intent(DashboardActivity.this, FullScreenActivity.class);
                intent.putExtra("videoPath", videoPath);
                startActivity(intent);
                break;
        }
//        if (view.getId() == R.id.cvProfile) {
//            startActivity(new Intent(DashboardActivity.this, ProfilActivity.class));
//        }
    }

    private void moveToLogin() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
