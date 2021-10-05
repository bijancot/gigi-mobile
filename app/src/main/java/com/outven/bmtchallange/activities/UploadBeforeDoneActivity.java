package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;

import static com.outven.bmtchallange.activities.UploadBeforeActivity.file;

public class UploadBeforeDoneActivity extends AppCompatActivity {
    ImageView imgUploadBeforeDone;
    Button btnUploadBeforeDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_before_done);

        imgUploadBeforeDone = findViewById(R.id.imgUploadBeforeDone);
        btnUploadBeforeDone = findViewById(R.id.btnUploadBeforeDone);

        imgUploadBeforeDone.setImageURI(file);

        btnUploadBeforeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Image Uploaded!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UploadBeforeDoneActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                startActivity(intent);
                finish();
            }
        });

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }
}