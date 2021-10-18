package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

    TextView signUp, txtForget;
    Button btnLogin;
    EditText etEmail,etPassword;
    String username, password;

    SessionManager sessionManager;
    LoginDataResponse loginDataResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        signUp = (TextView) findViewById(R.id.txtSignUp);
        txtForget = findViewById(R.id.txtForget);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                finish();
            }
        });
        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LupaPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                finish();
            }
        });

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin){
            username = etEmail.getText().toString();
            password = etPassword.getText().toString();

            userLogin(username, password);
        }
    }
    //    private LoginRequest createRequest() {
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail(etEmail.getText().toString());
//        loginRequest.setPassword(etPassword.getText().toString());
//
//        return loginRequest;
//    }

    private void userLogin(String username, String password) {
        Call<LoginDataResponse> userList = ApiClient.getUserService().userLogin(username, password);
        userList.enqueue(new Callback<LoginDataResponse>() {

            @Override
            public void onResponse(@NotNull Call<LoginDataResponse> call, @NotNull Response<LoginDataResponse> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    loginDataResponse = response.body();
                    sessionManager = new SessionManager(LoginActivity.this);
                    sessionManager.LoginSession(loginDataResponse.getLoginData());
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    overridePendingTransition(R.anim.from_right, R.anim.to_left);
                    finish();
                    Toast.makeText(getApplicationContext(), ""+loginDataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.body()!=null && !response.body().isStatus() && response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginDataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Login Trouble", Toast.LENGTH_SHORT).show();
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}