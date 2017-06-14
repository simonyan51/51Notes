package com.example.a51notes.utils;

/**
 * Created by simonyan51 on 6/4/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class PreferancesHelper {

    private static final String PREFERENCES_NAME = "preferences";

    private static final String LOGGED_IN = "logged_in";
    private static final String USER_ID = "user_id";

    private SharedPreferences preferences;

    private static PreferancesHelper instance;

    public static PreferancesHelper getInstance(Context context){
        if(instance == null){
            instance = new PreferancesHelper(context.getApplicationContext());
        }

        return instance;
    }

    private PreferancesHelper(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void resetAll() {
        final SharedPreferences.Editor editor = getEditor();

        editor.remove(LOGGED_IN);
        editor.remove(USER_ID);

        editor.apply();
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(LOGGED_IN, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        final SharedPreferences.Editor editor = getEditor();

        editor.putBoolean(LOGGED_IN, loggedIn);
        editor.apply();
    }

    public long getUserId() {
        return preferences.getLong(USER_ID, -1);
    }

    public void setUserId(long userId) {
        final SharedPreferences.Editor editor = getEditor();

        editor.putLong(USER_ID, userId);
        editor.apply();
    }
}