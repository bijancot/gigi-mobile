package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    static final String[] trackerDay = {
            "1","2","3","4","5","6","7",
            "8","9","10","11","12","13","14",
            "15","16","17","18","19","20","21"};
    String videoPath;
    String name,tier;
    Date batas;
    Date curTime;

    TextView txtUsername, txtTier, txtTracker, tittleTracker;
    LinearLayout btnTracker, llBorderName, llBorderTier;
    GridView gvTracker;
    CardView cvProfile;
    RelativeLayout rlDashboard;
    ImageView ivTheme;


    SessionManager sessionManager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Find id
        cvProfile = findViewById(R.id.cvProfile);
        txtUsername = findViewById(R.id.txtUsername);
        txtTier = findViewById(R.id.txtTier);
        txtUsername = findViewById(R.id.txtUsername);
        tittleTracker = findViewById(R.id.tittleTracker);
        ivTheme = findViewById(R.id.ivTheme);
        gvTracker = (GridView) findViewById(R.id.gvTracker);
        VideoView vvTutorial = findViewById(R.id.vvTutorial);
        ImageButton ibFullScreen = findViewById(R.id.ibFullScreen);

        //cek curtime
        try {
            curTimeCheck();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Cek user login
        sessionManager = new SessionManager(DashboardActivity.this);
        if (!sessionManager.isLoggedIn()){
            moveToLogin();
        }


        //get sharedPreference values into string
        name = sessionManager.getUserDetail().get("name");
        tier = sessionManager.getUserDetail().get("tier");

        //set User detail Dashboard
        txtUsername.setText(name);
        txtTier.setText(tier);

        cvProfile.setOnClickListener(this);
        gvTracker.setAdapter(new TrackerAdapter());
        gvTracker.setOnItemClickListener(this);
//        gvTracker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DashboardActivity.this);
//                alertDialog2.setMessage("Apakah sudah melihat video tutorial cara sikat gigi?");
//                alertDialog2.setPositiveButton("Sudah",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                startActivity(new Intent(DashboardActivity.this, UploadBeforeActivity.class));
//                            }
//                        });
//                alertDialog2.setNegativeButton("Belum",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(DashboardActivity.this, FullScreenActivity.class);
//                                intent.putExtra("videoPath", videoPath);
//                                startActivity(intent);
//                            }
//                        });
//                alertDialog2.show();
//            }
//        });

        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videodashboard;
        Uri uri = Uri.parse(videoPath);
        vvTutorial.setVideoURI(uri);
        vvTutorial.seekTo(1000);
        MediaController mediaController = new MediaController(this);
        vvTutorial.setMediaController(mediaController);
        mediaController.setAnchorView(vvTutorial);

        ibFullScreen.setOnClickListener(this);
//        ibFullScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DashboardActivity.this, FullScreenActivity.class);
//                intent.putExtra("videoPath", videoPath);
//                startActivity(intent);
//            }
//        });

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }

    private void curTimeCheck() throws ParseException {

        rlDashboard = findViewById(R.id.rlDashboard);
        llBorderName = findViewById(R.id.llBorderName);
        llBorderTier = findViewById(R.id.llBorderTier);

        String yourTime = "06:30 PM";
        String today = (String) android.text.format.DateFormat.format(
                "h:mm a", new java.util.Date());
        SimpleDateFormat localtime = new SimpleDateFormat("h:mm a");
        batas = localtime.parse(yourTime);
        curTime = localtime.parse(today);
        if (curTime.after(batas)) {
            nightTheme();
        }
    }

    private void nightTheme() {
        rlDashboard.setBackgroundColor(getResources().getColor(R.color.nightColor));
        llBorderName.setBackgroundResource(R.drawable.custom_border_night);
        llBorderTier.setBackgroundResource(R.drawable.custom_border_night);
        txtUsername.setTextColor(getResources().getColor(R.color.purple));
        txtTier.setTextColor(getResources().getColor(R.color.purple));
        tittleTracker.setTextColor(getResources().getColor(R.color.white));
        ivTheme.setImageResource(R.drawable.tiernight);

        System.out.println("SEKARANG : "+curTime);
        System.out.println("BATAS : "+batas);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cvProfile:
                startActivity(new Intent(DashboardActivity.this, ProfilActivity.class));
                break;
            case R.id.ibFullScreen:
                Intent intent = new Intent(DashboardActivity.this, FullScreenActivity.class);
                intent.putExtra("videoPath", videoPath);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DashboardActivity.this);
        Toast.makeText(DashboardActivity.this, "Day "+ (i+1),Toast.LENGTH_SHORT).show();
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

    private class TrackerAdapter extends ArrayAdapter<String> {
        public TrackerAdapter() {
            super(DashboardActivity.this, R.layout.tracker_cell, trackerDay);
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return position == Integer.parseInt(sessionManager.getUserDetail().get("tier")) - 1;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.tracker_cell, parent, false);
            }
            btnTracker = (LinearLayout) row.findViewById(R.id.btnTracker);
            txtTracker = (TextView) row.findViewById(R.id.txtTracker);

            if (position > Integer.parseInt(sessionManager.getUserDetail().get("tier"))-1){
                btnTracker.setBackgroundResource(R.drawable.custom_border_tracker_disabled);
            } else {
                if (position < Integer.parseInt(sessionManager.getUserDetail().get("tier")) - 1){
                    btnTracker.setBackgroundResource(R.drawable.custom_border_tracker_done);
                } else {
                    btnTracker.setBackgroundResource(R.drawable.custom_border_tracker);
                }
            }
            txtTracker.setText(trackerDay[position]);
            return row;
        }
    }

    private void moveToLogin() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
