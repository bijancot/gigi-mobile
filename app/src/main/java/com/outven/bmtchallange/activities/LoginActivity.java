package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;
import com.outven.bmtchallange.models.login.Response.LoginDataResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSignUp, txtForget;
    Button btnLogin;
    EditText etEmail,etPassword;

    String email, password;

    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(LoginActivity.this);

        //Id Finder
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);
        txtForget = findViewById(R.id.txtForget);

        //Clickc Listener
        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForget.setOnClickListener(this);

        HidenBar.WindowFlag(LoginActivity.this, getWindow());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                createLoginRequest();
                break;
            case R.id.txtSignUp:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                moveToNextPage(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.txtForget:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                moveToNextPage(LoginActivity.this, LupaPasswordActivity.class);
                break;
        }
    }

    private void createLoginRequest(){
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        userLogin(email, password);
    }

    private void userLogin(String email, String password) {
        Call<LoginDataResponse> userList = ApiClient.getUserService().userLogin(email, password);
        userList.enqueue(new Callback<LoginDataResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginDataResponse> call, @NotNull Response<LoginDataResponse> response) {
                try {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                        LoginDataResponse loginDataResponse = response.body();
                        sessionManager.LoginSession(loginDataResponse.getLoginData(), password);
                        Toast.makeText(getApplicationContext(), ""+loginDataResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        moveToNextPage(LoginActivity.this, DashboardActivity.class);
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Server sedang bermasalah, coba beberapa saat lagi!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginDataResponse> call, @NotNull Throwable t) {
                try {
                    Toast.makeText(getApplicationContext(), "Server sedang bermasalah, coba login nanti!" , Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Server sedang bermasalah, coba login nanti!" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveToNextPage(Context context, Class<? extends Activity> activityClass){
        Intent intent = new Intent(context, activityClass);
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.isLoggedIn()){
            Log.e("----", ""+sessionManager.isLoggedIn());
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }
    }
    private long mLastClickTime = 0;
}