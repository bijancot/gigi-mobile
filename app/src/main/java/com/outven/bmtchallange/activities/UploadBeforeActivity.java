package com.outven.bmtchallange.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;

public class UploadBeforeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int IMAGE_CAPTURE_CODE = 1001;
    Uri file;
    CardView imgUploadBefore;
    Button btnUploadBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_before);

        imgUploadBefore = findViewById(R.id.imgUploadBefore);
        btnUploadBefore = findViewById(R.id.btnUploadBefore);

        permissionCheck();
        imgUploadBefore.setOnClickListener(this);
        btnUploadBefore.setOnClickListener(this);

        HidenBar.WindowFlag(this, getWindow());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgUploadBefore:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                ambilPoto();
                break;
            case R.id.btnUploadBefore:
                Toast.makeText(UploadBeforeActivity.this, "Kamu belum mengupload foto sebelum kamu sikat gigi!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imgUploadBefore.setEnabled(true);
            }
        }
    }

    private void ambilPoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Pict");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        file = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Config.setFileBefore(file);
            startActivity(new Intent(UploadBeforeActivity.this,UploadBeforeDoneActivity.class));
        }
    }

    private long mLastClickTime = 0;
}