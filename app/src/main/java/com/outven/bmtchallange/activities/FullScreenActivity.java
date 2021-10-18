package com.outven.bmtchallange.activities;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;

public class FullScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        VideoView vvTutorial = findViewById(R.id.vvTutorial);
        String videoPath;
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                videoPath = null;
            } else {
                videoPath = extras.getString("videoPath");
            }
        } else {
            videoPath = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        Uri uri = Uri.parse(videoPath);
        vvTutorial.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        vvTutorial.setMediaController(mediaController);
        vvTutorial.seekTo(1);
        vvTutorial.start();


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}