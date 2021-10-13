package com.outven.bmtchallange.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;

public class UploadBeforeActivity extends AppCompatActivity {
    private static final int IMAGE_CAPTURE_CODE = 1001;
    static Uri file;
    private TextView mTextView;
    CardView imgUploadBefore;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_before);
        context = this;

        imgUploadBefore = findViewById(R.id.imgUploadBefore);
        imgUploadBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilPoto();
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            imgUploadBefore.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
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
            startActivity(new Intent(UploadBeforeActivity.this,UploadBeforeDoneActivity.class));

//
//            Dialog dialog = new Dialog(context);
//            dialog.setContentView(R.layout.activity_alert);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            final ImageView imgDialog = (ImageView) dialog.findViewById(R.id.imgDialog);
//            Button btnTidak = (Button) dialog.findViewById(R.id.btnTidak);
//            Button btnYa = (Button) dialog.findViewById(R.id.btnYa);
//            imgDialog.setImageURI(file);
//
//            btnTidak.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            btnYa.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
        }
    }
}