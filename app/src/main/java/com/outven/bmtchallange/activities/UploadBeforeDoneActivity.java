package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;

public class UploadBeforeDoneActivity extends AppCompatActivity {
    ImageView imgUploadBeforeDone;
    Button btnUploadBeforeDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_before_done);

        imgUploadBeforeDone = findViewById(R.id.imgUploadBeforeDone);
        btnUploadBeforeDone = findViewById(R.id.btnUploadBeforeDone);

        imgUploadBeforeDone.setImageURI(Config.getFileBefore());

        btnUploadBeforeDone.setOnClickListener(view -> moveToPage());

        HidenBar.WindowFlag(this, getWindow());
    }
    public void moveToPage(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Intent intent = new Intent(UploadBeforeDoneActivity.this, AnimasiActivity.class);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        startActivity(intent);
    }

    private long mLastClickTime = 0;
}