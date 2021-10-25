package com.outven.bmtchallange.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;

import java.io.File;

public class UploadBeforeActivity extends AppCompatActivity implements View.OnClickListener {

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

        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();

            File imageFileBefore = new File(uri.getPath());
            Config.files[0] = imageFileBefore;
            Config.listUpload[0][0] = Config.getCategoryUpload();
            Config.listUpload[0][1] = "before";

            Config.setFileBefore(uri);

            startActivity(new Intent(UploadBeforeActivity.this,UploadBeforeDoneActivity.class));
        }
    }

    private long mLastClickTime = 0;
}