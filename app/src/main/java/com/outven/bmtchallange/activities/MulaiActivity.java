package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

public class MulaiActivity extends AppCompatActivity {

    Button btnSplashMulai;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);
        sessionManager = new SessionManager(MulaiActivity.this);
        btnSplashMulai = findViewById(R.id.btnSplashMulai);

        btnSplashMulai.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            sessionManager.StartAppSession();
            moveToNextPage();
        });

        HidenBar.WindowFlag(MulaiActivity.this, getWindow());
    }

    public void moveToNextPage(){
        Intent m = new Intent(MulaiActivity.this, LoginActivity.class);
        startActivityForResult(m,0);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }

    @Override
    protected void onResume() {
        if (sessionManager.isStartApp()){
//            sessionManager.changeStatusLoggedIn(false);
            moveToNextPage();
        }
        super.onResume();

    }

    private long mLastClickTime = 0;
}