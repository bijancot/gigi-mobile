package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

import java.util.Objects;

import io.sentry.Sentry;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    TextView etName, etPhone, etTanggalLahir,etKelas,etGender, etHp, etEmail;
    Button btnLogout, btnEdit;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        sessionManager = new SessionManager(ProfilActivity.this);
        try {
            refreshProfil();
        } catch (Exception e){
            Log.e("Error, ", e.getLocalizedMessage());
            sessionManager.loggoutSession();
            Toast.makeText(ProfilActivity.this,"Terjadi kesalahan pada server", Toast.LENGTH_LONG).show();
            moveToNextPage(ProfilActivity.this);
            Sentry.captureException(e);
        }

        HidenBar.WindowFlag(this, getWindow());
    }

    private void refreshProfil() {
        btnLogout = findViewById(R.id.btnLogout);
        btnEdit = findViewById(R.id.btnEdit);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etTanggalLahir = findViewById(R.id.etTanggalLahir);
        etPhone = findViewById(R.id.etPhone);
        etKelas = findViewById(R.id.etKelas);
        etHp = findViewById(R.id.etHp);
        etGender = findViewById(R.id.etGender);

        // setProfile
        etEmail.setText(sessionManager.getUserDetail().get(Config.USER_NISN));
        etName.setText(sessionManager.getUserDetail().get(Config.USER_NAME));
        etTanggalLahir.setText(sessionManager.getUserDetail().get(Config.USER_LAHIR));
        etHp.setText(sessionManager.getUserDetail().get(Config.USER_PHONE));
        etGender.setText(radioGenderChecked(Objects.requireNonNull(sessionManager.getUserDetail().get(Config.USER_GENDER))));
        etKelas.setText(sessionManager.getUserDetail().get(Config.USER_CLASS));

        btnLogout.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }

    private String radioGenderChecked(String gender) {
        if (gender.equalsIgnoreCase("1")){
            return "Laki - laki";
        } else {
            return  "Perempuan";
        }
    }

    private void moveToNextPage(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        if (true){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogout:

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                sessionManager.loggoutSession();
                Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                finish();
                break;
            case R.id.btnEdit:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(ProfilActivity.this, EditProfileActivity.class));
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                break;
        }
    }
    private long mLastClickTime = 0;
}