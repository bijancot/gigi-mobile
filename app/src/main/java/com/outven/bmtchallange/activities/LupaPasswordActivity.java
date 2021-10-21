package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.models.forgot.ForgotResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtEmail, txtPasswordLama, txtPasswordBaru;
    Button btnUpdate;
    String email,password,newpassowrd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswordLama = findViewById(R.id.txtPasswordLama);
        txtPasswordBaru = findViewById(R.id.txtPasswordBaru);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(this);

        HidenBar.WindowFlag(this, getWindow());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUpdate) {
            email = txtEmail.getText().toString();
            password = txtPasswordLama.getText().toString();
            newpassowrd = txtPasswordBaru.getText().toString();

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            userChangePassword(email,password,newpassowrd);
        }
    }

    private void userChangePassword(String email, String password, String newpassowrd) {
        Call<ForgotResponse> forgetResponseCall = ApiClient.getUserService().userChangePassword(email,password,newpassowrd);
        forgetResponseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(@NotNull Call<ForgotResponse> call, @NotNull Response<ForgotResponse> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    moveToLogin();
                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.body()!=null){
                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ForgotResponse> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(LupaPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToLogin();
    }
    private long mLastClickTime = 0;
}