package com.outven.bmtchallange;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtSignIn;
    EditText etEmail,etPassword, etName, etSekolah, etPhone, etKelas;
    Button btnSignUp;
    private EditText etTanggalLahir;
    int hari, bulan, tahun;
    private final String[] arrBulan = {"Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus", "September", "Oktober",
            "November", "Desember"};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etSekolah = (EditText) findViewById(R.id.etSekolah);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etKelas = (EditText) findViewById(R.id.etKelas);

        final Calendar c = Calendar.getInstance();
        hari = c.get(Calendar.DAY_OF_MONTH);
        bulan = c.get(Calendar.MONTH);
        tahun = c.get(Calendar.YEAR);
        etTanggalLahir = (EditText) findViewById(R.id.etTanggalLahir);
        etTanggalLahir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showDialog(0);
                return true;
            }
        });

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(createRequest());
            }
        });

        txtSignIn = (TextView) findViewById(R.id.txtSignIn);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                finish();
            }
        });

        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);

    }

    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(etEmail.getText().toString());
        userRequest.setName(etName.getText().toString());
        userRequest.setGender(1);
        userRequest.setBirth_date(etTanggalLahir.getText().toString());
        userRequest.setPhone_number(etPhone.getText().toString());
        userRequest.setSchool_name(etSekolah.getText().toString());
        userRequest.setSchool_class(etKelas.getText().toString());
        userRequest.setPassword(etPassword.getText().toString());

        return userRequest;
    }

    public void saveUser(UserRequest userRequest){
        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Sign Up Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.from_right, R.anim.to_left);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Request failed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Request failed" + t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this,
                        mDateSetListener, tahun, bulan, hari);
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener mDateSetListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    tahun = year;
                    bulan = monthOfYear;
                    hari = dayOfMonth;
                    String sdate = LPad(hari + "", "0", 2)
                            + " " + arrBulan[bulan] + " " + tahun;
                    etTanggalLahir.setText(sdate);
                }
            };

    public static String LPad(String schar, String spad, int len) {
        String sret = schar;
        for (int i = sret.length(); i < len; i++) {
            sret = spad + sret;
        }
        return new String(sret);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        finish();
    }
}