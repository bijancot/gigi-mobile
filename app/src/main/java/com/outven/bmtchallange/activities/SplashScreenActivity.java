package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SessionManager sessionManager = new SessionManager(this);
//        sessionManager.clearCommit();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent m = new Intent(SplashScreenActivity.this, MulaiActivity.class);
                    startActivityForResult(m, 0);
                }
            }
        };
        timer.start();

        HidenBar.WindowFlag(SplashScreenActivity.this, getWindow());
    }
}