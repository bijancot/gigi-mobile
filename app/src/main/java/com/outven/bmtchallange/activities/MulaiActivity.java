package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

public class MulaiActivity extends AppCompatActivity {

    Button btnSplashMulai;
    SessionManager sessionManager;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String starter = "starter";
    SharedPreferences sharedPreferences;

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

            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(starter, true);
            editor.apply();

            Config.setStarter(true);
            moveToNextPage(MulaiActivity.this, PrologActivity.class,true);
        });

        HidenBar.WindowFlag(MulaiActivity.this, getWindow());
    }

    public void moveToNextPage(Context context, Class<? extends Activity> activityClass, boolean setFlags){
        Intent intent = new Intent(context, activityClass);
        if (setFlags){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }

    @Override
    protected void onResume() {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(starter)){
            if (sharedPreferences.getBoolean(starter,false)){
                moveToNextPage(MulaiActivity.this, PrologActivity.class,true);
            }
//            sessionManager.changeStatusLoggedIn(false);
        }
        super.onResume();

    }

    private long mLastClickTime = 0;
}