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
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.models.ApiClient;
import com.outven.bmtchallange.models.LoginRequest;
import com.outven.bmtchallange.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    Button btnLogin;
    EditText etEmail,etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(createRequest());
            }
        });

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

    private LoginRequest createRequest() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(etEmail.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        return loginRequest;
    }

    private void userLogin(LoginRequest loginRequest) {
        Call<LoginResponse> userList = ApiClient.getUserService().userLogin(loginRequest);
        userList.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null && response.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    overridePendingTransition(R.anim.from_right, R.anim.to_left);
                    finish();
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Log.e("success", response.body().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_SHORT).show();
                    Log.e("failed", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Login Trouble", Toast.LENGTH_SHORT).show();
                Log.e("failure", t.getLocalizedMessage());
            }

//            @Override
//            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
//
//            }
        });
    }
}