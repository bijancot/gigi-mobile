package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtLogout,txtName;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        sessionManager = new SessionManager(ProfilActivity.this);

        txtLogout = findViewById(R.id.txtLogout);
        txtName = findViewById(R.id.txtName);

        txtName.setText(sessionManager.getUserDetail().get("name"));

        txtLogout.setOnClickListener(this);

        HidenBar.WindowFlag(this, getWindow());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtLogout){
            sessionManager.loggoutSession();
            Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.from_right, R.anim.to_left);
            finish();
        }
    }
}