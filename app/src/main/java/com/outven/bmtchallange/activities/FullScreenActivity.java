package com.outven.bmtchallange.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        VideoView vvTutorial = findViewById(R.id.vvTutorial);

        Uri uri = Uri.parse(Config.getVidePath());
        vvTutorial.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        vvTutorial.setMediaController(mediaController);
        vvTutorial.seekTo(10);
        vvTutorial.start();
    }
}