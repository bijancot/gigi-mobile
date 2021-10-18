package com.outven.bmtchallange.activities.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.activities.FullScreenActivity;
import com.outven.bmtchallange.activities.UploadBeforeActivity;
import com.outven.bmtchallange.helper.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    Context _context;
    String[] listDayTracker;
    int userTrackerDay;
    String videoPath;
    Date tDayFirstTime, tDayLastTime,tNightFirstTime, tNightLastTime, tDayTime, tNightTime, tfirstDayTime;
    Date curTime;
    Config config = new Config();

    public MyAdapter(Context context, String[] listDayTracker, int userTrackerDay, String videoPath){
        this._context = context;
        this.listDayTracker = listDayTracker;
        this.userTrackerDay = userTrackerDay;
        this.videoPath = videoPath;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View trackerDone = LayoutInflater.from(_context).inflate(R.layout.tracker_cell_done,parent,false);
        View trackerNow = LayoutInflater.from(_context).inflate(R.layout.tracker_cell,parent,false);
        View trackerSoon = LayoutInflater.from(_context).inflate(R.layout.tracker_cell_soon,parent,false);

        if (viewType == 0){
            return  new MyViewHolderDone(trackerDone);
        } else if (viewType == 1){
            return new MyViewHolderNow(trackerNow);
        } else {
            return new MyViewHolderSoon(trackerSoon);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case 0:
                MyViewHolderDone viewHolderDone = (MyViewHolderDone) holder;
                viewHolderDone.txtTrackerDone.setText("Hari " + (position+1));
                break;
            case 1:
                MyViewHolderNow viewHolderNow = (MyViewHolderNow) holder;
                viewHolderNow.txtTrackerNow.setText("Hari " + (position+1));
                viewHolderNow.btnTracker.setOnClickListener(this);
                break;
            case 2:
                MyViewHolderSoon viewHolderSoon = (MyViewHolderSoon) holder;
                viewHolderSoon.txtTrackerSoon.setText("Hari " + (position+1));
                break;
        }
    }

    public void timeTrackerManager() throws ParseException {
        String today = (String) android.text.format.DateFormat.format(
                "h:mm a", new java.util.Date());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat localtime = new SimpleDateFormat("h:mm a");

        // Pagi
        String mfirstTime = "06:00 AM";
        String mlasTime = "08:00 AM";
        String mdayTime = "11:59 AM";
        String mfirstDayTime = "00:00 AM";
        tDayFirstTime = localtime.parse(mfirstTime);
        tDayLastTime = localtime.parse(mlasTime);
        tDayTime = localtime.parse(mdayTime);
        tfirstDayTime = localtime.parse(mfirstDayTime);

        // Malam
        String nfirstTime = "06:00 PM";
        String nlasTime = "09:00 PM";
        String nnightTime = "11:59 PM";
        tNightFirstTime = localtime.parse(nfirstTime);
        tNightLastTime = localtime.parse(nlasTime);
        curTime = localtime.parse(today);
        tNightTime = localtime.parse(nnightTime);

        //Now
        curTime = localtime.parse(today);
    }

    public int isMorning() {

        try {
            timeTrackerManager();
            if (curTime.after(tDayFirstTime) && curTime.before(tDayLastTime)) {
                return 0;
            } else if (curTime.after(tDayLastTime) && curTime.before(tNightFirstTime)){
                config.setmTimeTracker("Kamu sudah mengupload foto sikat gigi pagi! Upload foto lagi setelah jam 06.00 malam");
                return 1;
            } else if (curTime.after(tfirstDayTime) && curTime.before(tDayFirstTime)){
                config.setmTimeTracker("Kamu belum bisa mengupload foto sikat gigi! Upload foto sikat gigimu setelah jam 06.00 pagi");
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 2;
    }

    public int isNight(){
        try {
            timeTrackerManager();
            if (curTime.after(tNightFirstTime) && curTime.before(tNightLastTime)) {
                return 0;
            } else if (curTime.after(tNightLastTime) && curTime.before(tNightTime)){
                config.setmTimeTracker("Kamu sudah menyelesaikan chellenge hari ini");
                return 1;
            } else {
                config.setmTimeTracker("Kamu sudah mengupload foto sikat gigi!");
                return 2;
            }
        } catch (ParseException e) {
            config.setmTimeTracker(e.getLocalizedMessage().toString());
            return 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isDone(position)){
            return 0;
        } else if (isEnable(position)){
            return 1;
        }
        return 2;
    }

    public boolean isDone(int position){
        return position < userTrackerDay;
    }

    public boolean isEnable(int position){
        return position == userTrackerDay;
    }

    @Override
    public int getItemCount() {
        return listDayTracker.length;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnTracker){
            if (isMorning() == 0 || isNight() == 0){
                showAlert();
            } else {
                if (isMorning() == 1 || isNight() == 1 ||isMorning() == 2 || isNight() == 2){
                    Toast.makeText(_context, config.getmTimeTracker(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showAlert() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(_context);
        if (isMorning() == 0){
            alertDialog2.setMessage("Apakah kamu sudah sarapan pagi atau belum?");
        } else if (isNight() == 0){
            alertDialog2.setMessage("Apakah kamu sudah makan malam atau belum?");
        }
        alertDialog2.setPositiveButton("Sudah",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _context.startActivity(new Intent(_context, UploadBeforeActivity.class));
                    }
                });
        alertDialog2.setNegativeButton("Belum",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(_context, FullScreenActivity.class);
                        intent.putExtra("videoPath", videoPath);
                        _context.startActivity(intent);
                    }
                });
        alertDialog2.show();
    }

    public static class MyViewHolderDone extends RecyclerView.ViewHolder {
        TextView txtTrackerDone;
        public MyViewHolderDone(@NonNull View itemView) {
            super(itemView);
            txtTrackerDone = (TextView) itemView.findViewById(R.id.txtTrackerDone);
        }
    }

    public static class MyViewHolderNow extends RecyclerView.ViewHolder {
        TextView txtTrackerNow;
        RelativeLayout btnTracker;

        public MyViewHolderNow(@NonNull View itemView) {
            super(itemView);
            txtTrackerNow = (TextView) itemView.findViewById(R.id.txtTrackerNow);
            btnTracker = (RelativeLayout) itemView.findViewById(R.id.btnTracker);
        }
    }

    public static class MyViewHolderSoon extends RecyclerView.ViewHolder {
        TextView txtTrackerSoon;
        public MyViewHolderSoon(@NonNull View itemView) {
            super(itemView);
            txtTrackerSoon = (TextView) itemView.findViewById(R.id.txtTrackerSoon);
        }
    }
}
