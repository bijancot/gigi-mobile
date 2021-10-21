package com.outven.bmtchallange.activities.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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
import com.outven.bmtchallange.activities.UploadBeforeActivity;
import com.outven.bmtchallange.helper.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    Context _context;
    String[] listDayTracker;
    int userTrackerDay;
    Date tDayFirstTime, tDayLastTime,tNightFirstTime, tNightLastTime, tDayTime, tNightTime, tfirstDayTime;
    Date curTime;
    String reportStatus;

    public MyAdapter(Context context, String[] listDayTracker, int userTrackerDay, String reportStatus){
        this._context = context;
        this.listDayTracker = listDayTracker;
        this.userTrackerDay = userTrackerDay;
        this.reportStatus = reportStatus;
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
        String mfirstTime = "00:00 AM"; //awal upload pagi
        String mlasTime = "11:59 AM"; //akhir upload pagi
        String mdayTime = "11:59 AM";
        String mfirstDayTime = "00:00 AM";
        tDayFirstTime = localtime.parse(mfirstTime);
        tDayLastTime = localtime.parse(mlasTime);
        tDayTime = localtime.parse(mdayTime);
        tfirstDayTime = localtime.parse(mfirstDayTime);

        // Malam
        String nfirstTime = "12:00 PM"; //awal upload malam
        String nlasTime = "11:59 PM"; //awal upload malam
        String nnightTime = "11:59 PM";
        tNightFirstTime = localtime.parse(nfirstTime);
        tNightLastTime = localtime.parse(nlasTime);
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
                Config.setmTimeTracker("Kamu sudah mengupload foto sikat gigi pagi! Upload foto lagi setelah jam 06.00 malam");
                return 1;
            } else if (curTime.after(tfirstDayTime) && curTime.before(tDayFirstTime)){
                Config.setmTimeTracker("Kamu belum bisa mengupload foto sikat gigi! Upload foto sikat gigimu setelah jam 06.00 pagi");
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
                Config.setmTimeTracker("Kamu sudah menyelesaikan chellenge hari ini");
                return 1;
            } else {
                Config.setmTimeTracker("Kamu sudah mengupload foto sikat gigi!");
                return 2;
            }
        } catch (ParseException e) {
            Config.setmTimeTracker(e.getLocalizedMessage());
            return 2;
        }
    }

    public Boolean isOnGoing(){
        return reportStatus.equalsIgnoreCase("ongoing");
    }

    @Override
    public int getItemViewType(int position) {
        if (isDone(position)){
            return 0;
        } else if (isEnable(position)){
            return 1;
        }
//        Log.e("failure ----", reportStatus+" - "+isOnGoing());
        return 2;
    }

    public boolean isDone(int position){
        return position < userTrackerDay-1;
    }

    public boolean isEnable(int position){
        return position == userTrackerDay-1;
    }

    @Override
    public int getItemCount() {
        return listDayTracker.length;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnTracker){
            if (isMorning() == 0 || isNight() == 0){
                //just do Oneclick
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                showAlert();
            } else {
                if (isMorning() == 1 || isNight() == 1 ||isMorning() == 2 || isNight() == 2){
                    Toast.makeText(_context, Config.getmTimeTracker(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showAlert()  {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(_context);
        if (isMorning() == 0){
            alertDialog2.setMessage("Apakah kamu sudah sarapan pagi atau belum?");
        } else if (isNight() == 0){
            alertDialog2.setMessage("Apakah kamu sudah makan malam atau belum?");
        }
        alertDialog2.setPositiveButton("Sudah",
                (dialog, which) -> _context.startActivity(new Intent(_context, UploadBeforeActivity.class)));

        alertDialog2.setNegativeButton("Belum",
                (dialog, which) -> dialog.dismiss());
        alertDialog2.show();
    }

    public static class MyViewHolderDone extends RecyclerView.ViewHolder {
        TextView txtTrackerDone;
        public MyViewHolderDone(@NonNull View itemView) {
            super(itemView);
            txtTrackerDone = itemView.findViewById(R.id.txtTrackerDone);
        }
    }

    public static class MyViewHolderNow extends RecyclerView.ViewHolder {
        TextView txtTrackerNow;
        RelativeLayout btnTracker;

        public MyViewHolderNow(@NonNull View itemView) {
            super(itemView);
            txtTrackerNow = itemView.findViewById(R.id.txtTrackerNow);
            btnTracker = itemView.findViewById(R.id.btnTracker);
        }
    }

    public static class MyViewHolderSoon extends RecyclerView.ViewHolder {
        TextView txtTrackerSoon;
        public MyViewHolderSoon(@NonNull View itemView) {
            super(itemView);
            txtTrackerSoon = itemView.findViewById(R.id.txtTrackerSoon);
        }
    }

    private long mLastClickTime = 0;
}
