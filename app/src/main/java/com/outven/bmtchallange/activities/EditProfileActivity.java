package com.outven.bmtchallange.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.outven.bmtchallange.R;
import com.outven.bmtchallange.api.ApiClient;
import com.outven.bmtchallange.helper.Config;
import com.outven.bmtchallange.helper.HidenBar;
import com.outven.bmtchallange.helper.SessionManager;
import com.outven.bmtchallange.models.profile.UpdateProfileResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    int hari, bulan, tahun;
    int gender;
    String strGender;
    String email,name,birth_date,phone_number,school_class;

    AutoCompleteTextView etKelas;
    EditText etName, etPhone, etTanggalLahir, etHp, etEmail;
    RadioGroup rgGender;
    RadioButton rbLaki, rbPerempuan, selectGender;
    Button btnSimpan;


    SessionManager sessionManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(EditProfileActivity.this);

        try {
            refreshEditProfile();
        } catch (Exception e){
            Log.e("Error, ", e.getLocalizedMessage());
            sessionManager.loggoutSession();
            Toast.makeText(EditProfileActivity.this,"Terjadi kesalahan pada server", Toast.LENGTH_LONG).show();
            moveToNextPage(EditProfileActivity.this);
        }


        //Hiden bar
        HidenBar.WindowFlag(this, getWindow());
    }

    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    private void refreshEditProfile() {
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etTanggalLahir = findViewById(R.id.etTanggalLahir);
        etPhone = findViewById(R.id.etHp);
        etKelas = findViewById(R.id.etKelas);
        etHp = findViewById(R.id.etHp);
        rgGender = findViewById(R.id.rgGender);
        rbLaki = findViewById(R.id.rbLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        btnSimpan = findViewById(R.id.btnSimpan);

        etEmail.setEnabled(false);
        etEmail.setFocusableInTouchMode(false);

        //Set EditText
        etEmail.setText(sessionManager.getUserDetail().get(Config.USER_NISN));
        etName.setText(sessionManager.getUserDetail().get(Config.USER_NAME));
        etTanggalLahir.setText(sessionManager.getUserDetail().get(Config.USER_LAHIR));
        etHp.setText(sessionManager.getUserDetail().get(Config.USER_PHONE));
        strGender = sessionManager.getUserDetail().get(Config.USER_GENDER);
        assert strGender != null;
        radioGenderChecked(strGender);
        etKelas.setText(sessionManager.getUserDetail().get(Config.USER_CLASS));

        String[] optionKelas = {"1","2","3","4","5","6"};
        ArrayAdapter<String> arrayAdapterKelas = new ArrayAdapter<>(EditProfileActivity.this, R.layout.dropdown_kelas, optionKelas);
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

        btnSimpan.setOnClickListener(this);
    }

    private void moveToNextPage(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void radioGenderChecked(String gender) {
        if (gender.equalsIgnoreCase("1")){
            rbLaki.setChecked(true);
        } else {
            rbPerempuan.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSimpan){
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            UpdateProfile();
        }
    }

    private void UpdateProfile() {
        email = etEmail.getText().toString();
        name = etName.getText().toString();

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

        if (!isUpdateFieldEmpety()){
            Toast.makeText(getApplicationContext(),"Semua input harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
            UpdateUserProfile(email,name,gender,birth_date,phone_number,school_class);
        }
    }

    private void UpdateUserProfile(String email, String name, int gender, String birth_date, String phone_number, String school_class) {
        Call<UpdateProfileResponse> updateProfileResponseCall = ApiClient.getUserService().userUpdateProfile(email,name,gender,birth_date,phone_number,school_class);
        updateProfileResponseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateProfileResponse> call, @NotNull Response<UpdateProfileResponse> response) {
                try {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                        Toast.makeText(EditProfileActivity.this, ""+response.body().getMessage() ,Toast.LENGTH_SHORT).show();
                        sessionManager.UpdateProfileSession(email, name,String.valueOf(gender),birth_date,phone_number,school_class);
                        moveToLogin();
                    } else {
                        assert response.body() != null;
                        Toast.makeText(EditProfileActivity.this, ""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(EditProfileActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateProfileResponse> call, @NotNull Throwable t) {
                try {
                    Toast.makeText(EditProfileActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(EditProfileActivity.this, "Server sedang bermaslah, silahkan coba beberapa saat lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        finish();
    }

    private boolean isUpdateFieldEmpety(){
        return (!etEmail.getText().toString().isEmpty() &&
                !etName.getText().toString().isEmpty() &&
                !etTanggalLahir.getText().toString().isEmpty() &&
                !(rgGender.getCheckedRadioButtonId() == -1) &&
                !etPhone.getText().toString().isEmpty() &&
                !etKelas.getText().toString().isEmpty()) ||
                (!etEmail.getText().toString().equalsIgnoreCase("") &&
                        !etName.getText().toString().equalsIgnoreCase("") &&
                        !etTanggalLahir.getText().toString().equalsIgnoreCase("") &&
                        !(rgGender.getCheckedRadioButtonId() == -1) &&
                        !etPhone.getText().toString().equalsIgnoreCase("") &&
                        !etKelas.getText().toString().equalsIgnoreCase(""));
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

    private long mLastClickTime = 0;
}