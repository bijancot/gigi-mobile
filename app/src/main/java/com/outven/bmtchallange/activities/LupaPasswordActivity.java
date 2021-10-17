package com.outven.bmtchallange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.models.Forgot.ForgotResponse;

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


        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUpdate) {
            email = txtEmail.getText().toString();
            password = txtPasswordLama.getText().toString();
            newpassowrd = txtPasswordBaru.getText().toString();
            
            userChangePassword(email,password,newpassowrd);
        }
    }

    private void userChangePassword(String email, String password, String newpassowrd) {
        Call<ForgotResponse> forgetResponseCall = ApiClient.getUserService().userChangePassword(email,password,newpassowrd);
        forgetResponseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(@NotNull Call<ForgotResponse> call, @NotNull Response<ForgotResponse> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    ForgotResponse forgotResponse = response.body();
                    moveToLogin();
                    Toast.makeText(getApplicationContext(), ""+forgotResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

//    public void updatePassword(View view) {
//        userChangePassword(createRequest());
//    }

//    private void userChangePassword(ForgetRequest forgetRequest) {
//        Call<ForgetResponse> userList = ApiClient.getUserService().userChangePassword(forgetRequest);
//        userList.enqueue(new Callback<ForgetResponse>(){
//
//            @Override
//            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
//
//                ForgetResponse myResponse = response.body();
//                if (response.body() != null && response.isSuccessful()){
//                    startActivity(new Intent(LupaPasswordActivity.this, LoginActivity.class));
//                    overridePendingTransition(R.anim.from_right, R.anim.to_left);
//                    finish();
//                    Toast.makeText(getApplicationContext(), ""+myResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ForgetResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Somthing Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void getForgotResult() {
//        Call<List<ForgetResponse>> resultList = ApiClient.getUserService().getForgotResult();
//        resultList.enqueue(new Callback<List<ForgetResponse>>() {
//            @Override
//            public void onResponse(Call<List<ForgetResponse>> call, Response<List<ForgetResponse>> response) {
//                List<ForgetResponse> myResultForgot = response.body();
//                for (int i=0;i< myResultForgot.size();i++){
//                    rMessage = myResultForgot.get(i).getMessage().toString();
//                    rStatus = myResultForgot.get(i).getStatus();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ForgetResponse>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Simthing Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private ForgetRequest createRequest() {
//        ForgetRequest forgetRequest = new ForgetRequest();
//        forgetRequest.setEmail(txtEmail.getText().toString());
//        forgetRequest.setPassword(txtPasswordLama.getText().toString());
//        forgetRequest.setNewpassword(txtPasswordBaru.getText().toString());
//        return forgetRequest;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToLogin();
    }
}