package com.outven.bmtchallange.activities.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.activities.UploadBeforeActivity;
import com.outven.bmtchallange.helper.Config;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    Context _context;
    String[] listDayTracker;
    int userTrackerDay;
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

    public int checkTracker(){
        if (isOnGoing()){
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
        } else {
            return 4;
        }
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
        if (view.getId() == R.id.btnTracker){
            if (checkTracker() == 1) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                showAlert();
            } else {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String text = Config.getMessageTracker();
                showMessageAlert(text);
            }
        } else if (view.getId() == R.id.btnTrackerDanger){
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String text = "Maaf kamu gagal untuk upload hari ini\nSilahkan ulangi dari hari pertama";
            showMessageAlert(text);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showMessageAlert(String message) {
        Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_message_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txtDialogMessage = dialog.findViewById(R.id.txtDialogMessage);
        Button btnDialogMessage = dialog.findViewById(R.id.btnDialogMessage);

        txtDialogMessage.setText(message);
        btnDialogMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showAlert()  {
        Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_upload_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txtUploadDialogMessage = dialog.findViewById(R.id.txtUploadDialogMessage);
        Button btnUploadDialogBelum = dialog.findViewById(R.id.btnUploadDialogBelum);
        Button btnUploadDialogSudah = dialog.findViewById(R.id.btnUploadDialogSudah);

        if (time.equals(Config.TIME_DAY)){
            Config.setCategoryUpload("day");
            txtUploadDialogMessage.setText("Apakah kamu sudah sarapan pagi atau belum?");
        } else if (time.equals(Config.TIME_NIGHT)){
            Config.setCategoryUpload("night");
            txtUploadDialogMessage.setText("Apakah kamu sudah makan malam atau belum?");
        }

        btnUploadDialogBelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertIfHaveEat();
                dialog.dismiss();
            }
        });
        btnUploadDialogSudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _context.startActivity(new Intent(_context, UploadBeforeActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showAlertIfHaveEat() {
        Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_upload_makan);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnUplaodMakanOk = dialog.findViewById(R.id.btnUplaodMakanOk);

        btnUplaodMakanOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
