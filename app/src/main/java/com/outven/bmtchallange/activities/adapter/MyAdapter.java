package com.outven.bmtchallange.activities.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.outven.bmtchallange.activities.UploadBeforeActivity;
import com.outven.bmtchallange.helper.Config;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    Context _context;
    String[] listDayTracker;
    int userTrackerDay;
//    Date tDayFirstTime, tDayLastTime,tNightFirstTime, tNightLastTime, tDayTime, tNightTime, tfirstDayTime;
//    Date curTime;
    String reportStatus;
    String time;
    int entry;

    public MyAdapter(Context context, String[] listDayTracker, int userTrackerDay, String reportStatus, String time, int entry){
        this._context = context;
        this.listDayTracker = listDayTracker;
        this.userTrackerDay = userTrackerDay;
        this.reportStatus = reportStatus;
        this.time = time;
        this.entry = entry;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View trackerDone = LayoutInflater.from(_context).inflate(R.layout.tracker_cell_done,parent,false);
        View trackerNow = LayoutInflater.from(_context).inflate(R.layout.tracker_cell,parent,false);
        View trackerDanger = LayoutInflater.from(_context).inflate(R.layout.tracker_cell_danger,parent,false);
        View trackerSoon = LayoutInflater.from(_context).inflate(R.layout.tracker_cell_soon,parent,false);

        if (viewType == 0){
            return  new MyViewHolderDone(trackerDone);
        } else if (viewType == 1){
            return new MyViewHolderNow(trackerNow);
        } else if (viewType == 2){
            return new MyViewHolderDanger(trackerDanger);
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
                MyViewHolderDanger viewHolderDanger = (MyViewHolderDanger) holder;
                viewHolderDanger.txtTrackerDanger.setText("Hari " + (position+1));
                viewHolderDanger.btnTrackerDanger.setOnClickListener(this);
                break;
            case 3:
                MyViewHolderSoon viewHolderSoon = (MyViewHolderSoon) holder;
                viewHolderSoon.txtTrackerSoon.setText("Hari " + (position+1));
                break;
        }
    }

//    public void timeTrackerManager() throws ParseException {
//        String today = (String) android.text.format.DateFormat.format(
//                "h:mm a", new java.util.Date());
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat localtime = new SimpleDateFormat("h:mm a");
//
//        // Pagi
//        String mfirstTime = "00:00 AM"; //awal upload pagi
//        String mlasTime = "11:59 AM"; //akhir upload pagi
//        String mdayTime = "11:59 AM";
//        String mfirstDayTime = "00:00 AM";
//        tDayFirstTime = localtime.parse(mfirstTime);
//        tDayLastTime = localtime.parse(mlasTime);
//        tDayTime = localtime.parse(mdayTime);
//        tfirstDayTime = localtime.parse(mfirstDayTime);
//
//        // Malam
//        String nfirstTime = "12:00 PM"; //awal upload malam
//        String nlasTime = "11:59 PM"; //awal upload malam
//        String nnightTime = "11:59 PM";
//        tNightFirstTime = localtime.parse(nfirstTime);
//        tNightLastTime = localtime.parse(nlasTime);
//        tNightTime = localtime.parse(nnightTime);
//
//        //Now
//        curTime = localtime.parse(today);
//    }
    public int checkTracker(){
        if (time.equals(Config.TIME_DAY) && entry == 0 ){
            return 1;
        } else if (time.equals(Config.TIME_DAY) && entry == 2){
            Config.setMessageTracker("Kamu sudah mengupload foto sikat gigi pagi!");
            return 3;
        } else {
            if (time.equals(Config.TIME_NIGHT) && entry == 2) {
                return 1;
            } else if (time.equals(Config.TIME_NIGHT) && entry == 0) {
                return 2;
            } else {
                Config.setMessageTracker("Kamu sudah mengupload foto sikat gigi pagi!");
                return 4;
            }
        }
    }
    public int isMorning() {
        if (time.equalsIgnoreCase(Config.TIME_DAY) && entry == 0 ){
            return 1;
        } else {
            Config.setMessageTracker("Kamu sudah mengupload foto sikat gigi pagi!");
            return 0;
        }
//        try {
//            timeTrackerManager();
//            if (curTime.after(tDayFirstTime) && curTime.before(tDayLastTime)) {
//                return 0;
//            } else if (curTime.after(tDayLastTime) && curTime.before(tNightFirstTime)){
//                Config.setmTimeTracker("Kamu sudah mengupload foto sikat gigi pagi! Upload foto lagi setelah jam 06.00 malam");
//                return 1;
//            } else if (curTime.after(tfirstDayTime) && curTime.before(tDayFirstTime)){
//                Config.setmTimeTracker("Kamu belum bisa mengupload foto sikat gigi! Upload foto sikat gigimu setelah jam 06.00 pagi");
//                return 1;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return 2;
    }

    public int isNight(){
//        try {
//            timeTrackerManager();
//            if (curTime.after(tNightFirstTime) && curTime.before(tNightLastTime)) {
//                return 0;
//            } else if (curTime.after(tNightLastTime) && curTime.before(tNightTime)){
//                Config.setmTimeTracker("Kamu sudah menyelesaikan chellenge hari ini");
//                return 1;
//            } else {
//                Config.setmTimeTracker("Kamu sudah mengupload foto sikat gigi!");
//                return 2;
//            }
//        } catch (ParseException e) {
//            Config.setmTimeTracker(e.getLocalizedMessage());
//            return 2;
//        }

        if (time.equalsIgnoreCase(Config.TIME_NIGHT) && entry == 2) {
            return 0;
        } else if (time.equalsIgnoreCase(Config.TIME_NIGHT) && entry == 0) {
            return 1;
        } else if (time.equalsIgnoreCase(Config.TIME_NIGHT) && entry == 4) {
            return 2;
        }
        return 2;
    }

    public Boolean isOnGoing(){
        return reportStatus.equalsIgnoreCase("ongoing");
    }
    public boolean isDone(int position){
        return position + 1 < userTrackerDay;
    }
    public boolean isEnable(int position){
        return position + 1 == userTrackerDay;
    }

    @Override
    public int getItemViewType(int position) {
//        Log.e("time", time);
//        Log.e("entry", String.valueOf(entry));
        if (isDone(position)){
            return 0;
        } else if (isEnable(position)){
            if (checkTracker() == 1) {
                return 1;
            } else if (checkTracker() == 2){
                return 2;
            } else if (checkTracker() == 3){
                return 1;
            } else {
                return 0;
            }
        } else {
            return 3;
        }
    }


    @Override
    public int getItemCount() {
        return listDayTracker.length;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnTracker || view.getId() == R.id.btnTrackerDanger){
            if (checkTracker() == 1 || checkTracker() == 2) {
                showAlert();
            } else {
                Toast.makeText(_context, Config.getMessageTracker(), Toast.LENGTH_SHORT).show();
            }
//            if ( time.equals(Config.TIME_DAY) || time.equals(Config.TIME_NIGHT)){
//                //just do Oneclick
//                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
//                    return;
//                }
//                mLastClickTime = SystemClock.elapsedRealtime();
//
//            } else {
//                if (isMorning() == 1 || isNight() == 1 || isNight() == 2){
//                    Toast.makeText(_context, Config.getMessageTracker(),Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }

    private void showAlert()  {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(_context);
        if (time.equals(Config.TIME_DAY)){
            Config.setCategoryUpload("day");
            alertDialog2.setMessage("Apakah kamu sudah sarapan pagi atau belum?");
        } else if (time.equals(Config.TIME_NIGHT)){
            Config.setCategoryUpload("night");
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

    private static class MyViewHolderDanger extends RecyclerView.ViewHolder {
        TextView txtTrackerDanger;
        RelativeLayout btnTrackerDanger;
        public MyViewHolderDanger(View itemView) {
            super(itemView);
            txtTrackerDanger = itemView.findViewById(R.id.txtTrackerDanger);
            btnTrackerDanger = itemView.findViewById(R.id.btnTrackerDanger);
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
