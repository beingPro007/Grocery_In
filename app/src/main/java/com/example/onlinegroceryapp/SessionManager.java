package com.example.onlinegroceryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;

public class SessionManager {
    private static final String PREF_NAME = "MyAppSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String KEY_USER_EMAIL = "userEmail";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUserEmail(String email) {
        editor.putString(KEY_USER_EMAIL, email);
        editor.apply();
        Log.d("SharedPrefs", "User email saved: " + email); // Add logging statement
    }

    public String getUserEmail() {
        String userEmail = pref.getString(KEY_USER_EMAIL, "");
        Log.d("SharedPrefs", "Retrieved user email: " + userEmail); // Add logging statement
        return userEmail;
    }
    public boolean doesSharedPreferencesExist(Context context, String sharedPreferencesName) {
        File sharedPreferencesFile = new File(context.getFilesDir().getParent() + "/shared_prefs/" + sharedPreferencesName + ".xml");
        return sharedPreferencesFile.exists();
    }
}
