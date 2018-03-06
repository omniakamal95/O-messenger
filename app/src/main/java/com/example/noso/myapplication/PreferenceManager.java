package com.example.noso.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;



/**
 * Created by NOSO on 11/29/2017.
 */

public class PreferenceManager extends AppCompatActivity {

    public static final String KEY_NAME = "Username";
    public static final String KEY_PASSWORD = "Password";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    Integer mode = 0;

    public PreferenceManager() {
    }

    public PreferenceManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("myPref", mode);
        editor = pref.edit();
    }

    public void LoginSession(String name, String Password) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASSWORD, Password);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user mail
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // return user
        return user;
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context,LoginActivity.class);
            context.startActivity(i);

        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    public void LogoutUser()
    {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }
}

