package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.R;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = (TextView) findViewById(R.id.txtSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
            }
        });

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);

    }

    public void Login(View view) {
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }
}