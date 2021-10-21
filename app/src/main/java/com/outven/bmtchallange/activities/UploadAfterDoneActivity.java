package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;

public class UploadAfterDoneActivity extends AppCompatActivity {

    Button btnUploadAfterDone;
    ImageView imgUploadAfterDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_after_done);

        // Upload Code here

        imgUploadAfterDone = findViewById(R.id.imgUploadAfterDone);
        btnUploadAfterDone = findViewById(R.id.btnUploadAfterDone);

        imgUploadAfterDone.setImageURI(Config.getFileAfter());

        btnUploadAfterDone.setOnClickListener(view -> moveToDashboard());

        HidenBar.WindowFlag(this, getWindow());
    }

    public void moveToDashboard(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Toast.makeText(getApplicationContext(),"Image Uploaded!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UploadAfterDoneActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        finish();
    }
    private long mLastClickTime = 0;
}