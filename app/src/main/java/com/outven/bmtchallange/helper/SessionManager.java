package com.outven.bmtchallange.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.outven.bmtchallange.models.login.Response.LoginData;

import java.util.HashMap;

public class SessionManager {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SessionManager (Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void LoginSession(LoginData loginData, String password){
        editor.putBoolean(Config.IS_LOGGIN_IN,true);
        editor.putString(Config.USER_EMAIL, loginData.getUser().getEmail());
        editor.putString(Config.USER_PASSWORD, password);
        editor.putString(Config.USER_NAME, loginData.getUser().getName());
        editor.putString(Config.USER_TIER, loginData.getReport().getDay());
        editor.putString(Config.USER_REPORT_ID, loginData.getReport().getReportId());
        editor.putString(Config.USER_REPORT_STATUS, loginData.getReport().getStatus());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();

        user.put(Config.USER_EMAIL,sharedPreferences.getString(Config.USER_EMAIL,""));
        user.put(Config.USER_NAME,sharedPreferences.getString(Config.USER_NAME,""));
        user.put(Config.USER_PASSWORD,sharedPreferences.getString(Config.USER_PASSWORD,""));
        user.put(Config.USER_TIER,sharedPreferences.getString(Config.USER_TIER,""));
        user.put(Config.USER_REPORT_ID,sharedPreferences.getString(Config.USER_REPORT_ID,""));
        user.put(Config.USER_REPORT_STATUS,sharedPreferences.getString(Config.USER_REPORT_STATUS,""));
        return user;
    }

//    public HashMap<String, Integer> getUserDay(){
//        HashMap<String,Integer> user = new HashMap<>();
//        user.put(Config.USER_TIER,sharedPreferences.getInt(Config.USER_TIER,0));
//        return user;
//    }

    public void loggoutSession(){
        editor.clear();
        editor.commit();
    }

    public Boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Config.IS_LOGGIN_IN,false);
    }

//    public String thisDay(String athisDay){
//        return athisDay;
//    }
    public String lastDay(){
        return sharedPreferences.getString(Config.USER_OPEN_APP_DATE,"");
    }

    public Boolean haveOpenAppToday(String thisDay){
        if (!thisDay.equals(lastDay())){
            editor.putString(Config.USER_OPEN_APP_DATE,thisDay);
            editor.putBoolean(Config.HAVE_REPORT_TODAY, false);
            return false;
        }
        return true;
    }

    public Boolean haveReportToday(){
        return sharedPreferences.getBoolean(Config.HAVE_REPORT_TODAY,false);
    }

    public void ReportedToday(boolean result){
        editor.putBoolean(Config.HAVE_REPORT_TODAY,result);
        editor.commit();
    }

    public void changeDateLastDay(String result){
        editor.putString(Config.USER_OPEN_APP_DATE, result);
        editor.commit();
    }
}
