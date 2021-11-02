package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.models.register.Response.UserResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    int hari, bulan, tahun;
    int gender;
    String email,password,name,birth_date,phone_number,school_class;

    AutoCompleteTextView etKelas;
    EditText etEmail,etPassword, etName, etPhone, etTanggalLahir;
    Button btnSignUp;
    TextView txtSignIn;
    RadioGroup rgGender;
    RadioButton selectGender;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Find ID
        etEmail =findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etTanggalLahir = findViewById(R.id.etTanggalLahir);
        etPhone = findViewById(R.id.etPhone);
        etKelas = findViewById(R.id.etKelas);
        rgGender = findViewById(R.id.rgGender);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);

        String[] optionKelas = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> arrayAdapterKelas = new ArrayAdapter<>(RegisterActivity.this, R.layout.dropdown_kelas, optionKelas);
        etKelas.setAdapter(arrayAdapterKelas);

//        Kalender
        final Calendar c = Calendar.getInstance();
        hari = c.get(Calendar.DAY_OF_MONTH);
        bulan = c.get(Calendar.MONTH);
        tahun = c.get(Calendar.YEAR);
        etTanggalLahir.setOnTouchListener((view, motionEvent) -> {
            showDialog(0);
            return true;
        });

        btnSignUp.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);

        //Hiden bar
        HidenBar.WindowFlag(this, getWindow());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                createSignUp();
                break;
            case R.id.txtSignIn:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                moveToLogin(false);
                break;
        }
    }

    private void createSignUp() {
        if (!isRegisterFieldEmpety()){
            Toast.makeText(getApplicationContext(),"Semua input harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            name = etName.getText().toString();

//                gender = genderCheck(autoCompleteTextView);
            int checkId = rgGender.getCheckedRadioButtonId();
            selectGender = rgGender.findViewById(checkId);
            String tgender =selectGender.getText().toString();
            if (tgender.equalsIgnoreCase("Laki - laki")){
                gender = 1;
            } else {
                gender = 2;
            }

            birth_date = etTanggalLahir.getText().toString();
            phone_number = etPhone.getText().toString();
            school_class = etKelas.getText().toString();

            saveUser(email,password,name,gender,birth_date,phone_number,school_class);
        }
    }

    private boolean isRegisterFieldEmpety(){
        return !etEmail.getText().toString().isEmpty() ||
            !etPassword.getText().toString().isEmpty() ||
                !etName.getText().toString().isEmpty() ||
                !etTanggalLahir.getText().toString().isEmpty() ||
                !etPhone.getText().toString().isEmpty() ||
                !etKelas.getText().toString().isEmpty();
    }

    private int genderCheck(AutoCompleteTextView Tgender) {
        String valueGender = Tgender.getText().toString();
        if (valueGender.equalsIgnoreCase("Perempuan")){
            return 2;
        } else {
            return 1;
        }
    }

//    public boolean validationEmail(EditText etEmail){
//        String email = etEmail.getText().toString().trim();
//        return email.matches(emailPattern);
//    }

    public void saveUser(String email, String password, String name, int gender, String birth_date, String phone_number, String school_class){
        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(email,password,name,gender,birth_date,phone_number,school_class);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserResponse> call, @NotNull Response<UserResponse> response) {
                try {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                        Toast.makeText(RegisterActivity.this, ""+response.body().getMessage() ,Toast.LENGTH_SHORT).show();
                        moveToLogin(true);
                    } else {
                        Toast.makeText(RegisterActivity.this, ""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(RegisterActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserResponse> call, @NotNull Throwable t) {
                try {
                    Toast.makeText(RegisterActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(RegisterActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveToLogin(boolean setFlag) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        if (setFlag){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        finish();
    }

    public Dialog onCreateDialog(int id) {
        if (id == 0) {
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
                            + " " + Config.arrBulan[bulan] + " " + tahun;
                    etTanggalLahir.setText(sdate);
                }
            };

    public static String LPad(String schar, String spad, int len) {
        StringBuilder sret = new StringBuilder(schar);
        for (int i = sret.length(); i < len; i++) {
            sret.insert(0, spad);
        }
        return sret.toString();
    }

    @Override
    public void onBackPressed() {
        moveToLogin(false);
    }

    private long mLastClickTime = 0;
}