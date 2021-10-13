package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.outven.bmtchallange.models.ApiClient;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.R;
import com.outven.bmtchallange.models.UserRequest;
import com.outven.bmtchallange.models.UserResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView,etKelas;
    EditText etEmail,etPassword, etName, etSekolah, etPhone;
    Button btnSignUp;
    private EditText etTanggalLahir;
    int hari, bulan, tahun;
    private final String[] arrBulan = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December"};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Find ID
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etSekolah = (EditText) findViewById(R.id.etSekolah);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etKelas = (AutoCompleteTextView) findViewById(R.id.etKelas);
        textInputLayout = (TextInputLayout) findViewById(R.id.tiGender) ;
        autoCompleteTextView = findViewById(R.id.etGender);

//        Dropdown Gender
        String[] option_gender = {"Laki - laki","Perempuan"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.this, R.layout.dropdown_gender, option_gender);
        autoCompleteTextView.setAdapter(arrayAdapter);
        String[] optionKelas = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter arrayAdapterKelas = new ArrayAdapter(RegisterActivity.this, R.layout.dropdown_kelas, optionKelas);
        etKelas.setAdapter(arrayAdapterKelas);

//        Kalender
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
                if (!validationEmail(etEmail)){
                    Toast.makeText(getApplicationContext(),"Email Address tidak valid!", Toast.LENGTH_SHORT).show();
                } else {
                    if (etPassword.getText().toString().equalsIgnoreCase("") ||
                            etName.getText().toString().equalsIgnoreCase("") ||
                            etTanggalLahir.getText().toString().equalsIgnoreCase("") ||
                            etSekolah.getText().toString().equalsIgnoreCase("") ||
                            etPhone.getText().toString().equalsIgnoreCase("")||
                            etKelas.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(getApplicationContext(),"Semua input harus diisi!", Toast.LENGTH_SHORT).show();
                    } else {
                        saveUser(createRequest());
                    }
                }
            }
        });

        TextView txtSignIn = (TextView) findViewById(R.id.txtSignIn);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);
                startActivity(intent);
                finish();
            }
        });
//        HidenBar
        HidenBar hidenBar = new HidenBar();
        Window window = getWindow();
        hidenBar.WindowFlag(this, window);
    }

    public boolean validationEmail(EditText etEmail){
        String email = etEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public UserRequest createRequest(){
        int gender;
        String valueGender = autoCompleteTextView.getText().toString();
        if (valueGender.equalsIgnoreCase("Perempuan")){
            gender=2;
        } else {
            gender=1;
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(etEmail.getText().toString());
        userRequest.setName(etName.getText().toString());
        userRequest.setGender(gender);
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
                Toast.makeText(RegisterActivity.this, "Request failed " + t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }
}