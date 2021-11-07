package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;

public class PrologActivity extends AppCompatActivity {

    Button btnSplashMulai;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String prolog = "prolog";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolog);

        btnSplashMulai = findViewById(R.id.btnSplashMulai);
        btnSplashMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(prolog,true);
                editor.apply();

                Config.setProlog(true);
                moveToNextPage(PrologActivity.this, LoginActivity.class, true);
            }
        });

        HidenBar.WindowFlag(PrologActivity.this,getWindow());
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
        if (sharedPreferences.contains(prolog)){
            if (sharedPreferences.getBoolean(prolog,false)){
                moveToNextPage(PrologActivity.this, LoginActivity.class,true);
            }
        }
        super.onResume();
    }

    private long mLastClickTime = 0;
}