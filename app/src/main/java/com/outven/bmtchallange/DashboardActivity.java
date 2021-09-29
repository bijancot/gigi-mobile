package com.outven.bmtchallange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    static final String[] trackerDay = {
            "01","02","03","04","05","06","07",
            "08","09","10","11","12","13","14",
            "15","16","17","18","19","20","21"};

    Button btnTracker;
    TextView txtTracker;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        GridView gvTracker = (GridView) findViewById(R.id.gvTracker);
        gvTracker.setAdapter(new TrackerAdapter());


        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }

    private class TrackerAdapter extends ArrayAdapter<String> {
        public TrackerAdapter() {
            super(DashboardActivity.this, R.layout.tracker_cell, trackerDay);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.tracker_cell, parent, false);
            }

            btnTracker = (Button) row.findViewById(R.id.btnTracker);
            txtTracker = (TextView) row.findViewById(R.id.txtTracker);
            txtTracker.setText(trackerDay[position]);
            return row;
        }
    }
}
