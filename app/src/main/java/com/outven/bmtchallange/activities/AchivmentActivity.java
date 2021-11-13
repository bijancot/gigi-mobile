package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

public class AchivmentActivity extends AppCompatActivity {

    String mystring;

    Button btnOk;
    TextView txtAchivment;

    SessionManager sessionManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivment);
        sessionManager = new SessionManager(AchivmentActivity.this);
        txtAchivment = findViewById(R.id.txtAchivment);
        btnOk = findViewById(R.id.btnOk);
        try {
            mystring = sessionManager.getUserDetail().get(Config.USER_NAME);
        } catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        String[] arr = mystring.split(" ", 2);
        txtAchivment.setText("Selamat "+ arr[0]+"!");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextPage(AchivmentActivity.this);
            }
        });

        HidenBar.WindowFlag(this, getWindow());
    }

    private void moveToNextPage(Context context){
        Intent intent = new Intent(context, DashboardActivity.class);
        if (true){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }
}