package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

public class AnimasiActivity extends AppCompatActivity implements View.OnClickListener {

    String videoPathAnimasi;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animasi);

        sessionManager = new SessionManager(AnimasiActivity.this);
        videoAnimasiSetter();

        VideoView vvTutorial = findViewById(R.id.vvTutorial);
        Button btnSelesaiAnimasi = findViewById(R.id.btnSelesaiAnimasi);
        ImageButton ibFullScreen = findViewById(R.id.ibFullScreen);

        btnSelesaiAnimasi.setOnClickListener(this);
        ibFullScreen.setOnClickListener(this);

        Uri uri = Uri.parse(Config.getVidePath());
        vvTutorial.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        vvTutorial.setMediaController(mediaController);
        vvTutorial.seekTo(10);
        vvTutorial.start();

        HidenBar.WindowFlag(this, getWindow());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSelesaiAnimasi:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(AnimasiActivity.this, UploadAfterActivity.class));
                break;
            case R.id.ibFullScreen:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(AnimasiActivity.this, FullScreenActivity.class));
                break;
        }
    }


    private Boolean isMale(String gender){
        return gender.equalsIgnoreCase("1");
    }

    private void videoAnimasiSetter() {
        int dayTracker = Integer.parseInt(String.valueOf(sessionManager.getUserDetail().get(Config.USER_DAY)));
        if (isMale(sessionManager.getUserDetail().get(Config.USER_GENDER))){
            if (dayTracker <= 1){
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.co_lvl1;
            } else if (dayTracker >= 2 && dayTracker <= 10){
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.co_lvl210;
            } else if (dayTracker >= 11 && dayTracker <= 20){
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.co_lvl1120;
            } else {
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.cwk_lvl21;
            }
        } else {
            if (dayTracker <= 1){
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.cwk_lvl1;
            } else if (dayTracker >= 2 && dayTracker <= 10){
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.cwk_lvl210;
            } else if (dayTracker >= 11 && dayTracker <= 20){
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.cwk_lvl1120;
            } else {
                videoPathAnimasi = "android.resource://" + getPackageName() + "/" + R.raw.cwk_lvl21;
            }
        }
        Config.setVidePath(videoPathAnimasi);
    }

    private long mLastClickTime = 0;
}