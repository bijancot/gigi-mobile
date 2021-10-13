package com.outven.bmtchallange.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;

public class DashboardActivity extends AppCompatActivity {

    static final String[] trackerDay = {
            "1","2","3","4","5","6","7",
            "8","9","10","11","12","13","14",
            "15","16","17","18","19","20","21"};
    String videoPath;
    TextView txtTracker;
    GridView gvTracker;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gvTracker = (GridView) findViewById(R.id.gvTracker);
        VideoView vvTutorial = findViewById(R.id.vvTutorial);
        ImageButton ibFullScreen = findViewById(R.id.ibFullScreen);

        gvTracker.setAdapter(new TrackerAdapter());
        gvTracker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DashboardActivity.this);
                alertDialog2.setMessage("Apakah sudah melihat video tutorial cara sikat gigi?");
                alertDialog2.setPositiveButton("Sudah",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(DashboardActivity.this, UploadBeforeActivity.class));
                            }
                        });
                alertDialog2.setNegativeButton("Belum",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DashboardActivity.this, FullScreenActivity.class);
                                intent.putExtra("videoPath", videoPath);
                                startActivity(intent);
                            }
                        });
                alertDialog2.show();
            }
        });

        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videodashboard;
        Uri uri = Uri.parse(videoPath);
        vvTutorial.setVideoURI(uri);
        vvTutorial.seekTo(1000);
        MediaController mediaController = new MediaController(this);
        vvTutorial.setMediaController(mediaController);
        mediaController.setAnchorView(vvTutorial);

        ibFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FullScreenActivity.class);
                intent.putExtra("videoPath", videoPath);
                startActivity(intent);
            }
        });

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }

    private class TrackerAdapter extends ArrayAdapter<String> {
        public TrackerAdapter() {
            super(DashboardActivity.this, R.layout.tracker_cell, trackerDay);
        }
        public View getView(int position,View convertView,ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.tracker_cell, parent, false);
            }

            txtTracker = (TextView) row.findViewById(R.id.txtTracker);
            txtTracker.setText(trackerDay[position]);
            return row;
        }
    }
}
