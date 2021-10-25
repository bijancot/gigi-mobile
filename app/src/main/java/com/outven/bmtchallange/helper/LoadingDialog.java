package com.outven.bmtchallange.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.outven.bmtchallange.R;

public class LoadingDialog {

    private final Activity activity;
    private String message;
    private Dialog alertDialog;

    public LoadingDialog(Activity activity, String message){
        this.activity = activity;
        this.message = message;
    }

    @SuppressLint("InflateParams")
    public void startLoadingDialog(){
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.custom_loading_dialog);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txtLoading = alertDialog.findViewById(R.id.txtLoading);
        txtLoading.setText(message);
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();;
    }
}
