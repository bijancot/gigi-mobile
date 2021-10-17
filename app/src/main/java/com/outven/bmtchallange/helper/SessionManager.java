package com.outven.bmtchallange.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.outven.bmtchallange.models.login.Response.LoginData;

import java.util.HashMap;

public class SessionManager {

    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SessionManager (Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void LoginSession(LoginData loginData){
        editor.putBoolean(Config.IS_LOGGIN_IN,true);
        editor.putString(Config.USER_EMAIL, loginData.getUser().getEmail());
        editor.putString(Config.USER_NAME, loginData.getUser().getName());
        editor.putString(Config.USER_TIER, loginData.getReport().getDay());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();

        user.put(Config.USER_EMAIL,sharedPreferences.getString(Config.USER_EMAIL,""));
        user.put(Config.USER_NAME,sharedPreferences.getString(Config.USER_NAME,""));
        user.put(Config.USER_TIER,sharedPreferences.getString(Config.USER_TIER,""));
        return user;
    }


    public void loggoutSession(){
        editor.clear();
        editor.commit();
    }

    public Boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Config.IS_LOGGIN_IN,false);
    }
}
