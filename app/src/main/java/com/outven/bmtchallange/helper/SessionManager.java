package com.outven.bmtchallange.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.outven.bmtchallange.models.login.Response.LoginData;
import com.outven.bmtchallange.models.report.response.Report;

import java.util.HashMap;

public class SessionManager {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences sharedPreferencesAppStart;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences.Editor editorAppStart;

    @SuppressLint("CommitPrefEdits")
    public SessionManager (Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferencesAppStart = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editorAppStart = sharedPreferencesAppStart.edit();
    }

    public void LoginSession(LoginData loginData, String password){
        editor.putBoolean(Config.IS_LOGGIN_IN,true);
        editor.putString(Config.USER_NISN, loginData.getUser().getNisn());
        editor.putString(Config.USER_PASSWORD, password);
        editor.putString(Config.USER_NAME, loginData.getUser().getName());
        editor.putString(Config.USER_GENDER, loginData.getUser().getGender());
        editor.putString(Config.USER_LAHIR, loginData.getUser().getBirthDate());
        editor.putString(Config.USER_PHONE, loginData.getUser().getPhoneNumber());
        editor.putString(Config.USER_CLASS, loginData.getUser().getSchoolClass());
        editor.commit();
    }

    public void ReportSession(Report report){
        editor.putString(Config.USER_REPORT_ID, report.getReportId());
        editor.putString(Config.USER_REPORT_ENTRY, report.getEntry());
        editor.putString(Config.USER_REPORT_TIME, report.getTime());
        editor.putString(Config.USER_DAY, report.getDay());
        editor.putString(Config.USER_REPORT_STATUS, report.getStatus());
        editor.commit();
    }

    public void UpdateProfileSession(String email, String nama, String gender, String lahir, String phone, String kelas){
        editor.putString(Config.USER_NISN, email);
        editor.putString(Config.USER_NAME, nama);
        editor.putString(Config.USER_GENDER, gender);
        editor.putString(Config.USER_LAHIR, lahir);
        editor.putString(Config.USER_PHONE, phone);
        editor.putString(Config.USER_CLASS, kelas);
        editor.commit();
    }

    public void StartAppSession(){
        editorAppStart.putBoolean(Config.IS_START, true);
        editorAppStart.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();

        user.put(Config.USER_NISN,sharedPreferences.getString(Config.USER_NISN,""));
        user.put(Config.USER_NAME,sharedPreferences.getString(Config.USER_NAME,""));
        user.put(Config.USER_PASSWORD,sharedPreferences.getString(Config.USER_PASSWORD,""));
        user.put(Config.USER_GENDER,sharedPreferences.getString(Config.USER_GENDER,""));
        user.put(Config.USER_LAHIR,sharedPreferences.getString(Config.USER_LAHIR,""));
        user.put(Config.USER_PHONE,sharedPreferences.getString(Config.USER_PHONE,""));
        user.put(Config.USER_CLASS,sharedPreferences.getString(Config.USER_CLASS,""));

        user.put(Config.USER_REPORT_ID,sharedPreferences.getString(Config.USER_REPORT_ID,""));
        user.put(Config.USER_REPORT_ENTRY,sharedPreferences.getString(Config.USER_REPORT_ENTRY,""));
        user.put(Config.USER_REPORT_TIME,sharedPreferences.getString(Config.USER_REPORT_TIME,""));
        user.put(Config.USER_DAY,sharedPreferences.getString(Config.USER_DAY,""));
        user.put(Config.USER_REPORT_STATUS,sharedPreferences.getString(Config.USER_REPORT_STATUS,""));
        return user;
    }

    public void loggoutSession(){
        editor.clear();
        editor.commit();
    }

    public Boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Config.IS_LOGGIN_IN,false);
    }

    public Boolean isStartApp(){
        return sharedPreferencesAppStart.getBoolean(Config.IS_START,false);
    }

    public void changeStatusLoggedIn(boolean result){
        editor.putBoolean(Config.IS_LOGGIN_IN,result);
        editor.commit();
    }

    public void clearCommit(){
        editor.clear();
        editor.commit();
    }
}
