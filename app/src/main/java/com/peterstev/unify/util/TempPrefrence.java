package com.peterstev.unify.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TempPrefrence {
    public static final String PREFS_LOGIN_USERNAME_KEY = "_USERNAME";
    public static final String PREFS_LOGIN_PASSWORD_KEY = "_PASSWORD";
    public static final String PREFS_NAME = "myPrefsFile";
    public static final Boolean isLoggedIn = null;

    /**
     * Called to save supplied value in shared preferences against given key.
     *
     * @param context Context of caller activity
     * @param key     Key of value to save against
     * @param value   Value to save
     */

    public static void saveToPref(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString(PREFS_LOGIN_USERNAME_KEY, value);
        prefEditor.putString(PREFS_LOGIN_PASSWORD_KEY, value);
        prefEditor.putBoolean("isLoggedIn", true);
        try {
            prefEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called to retrieve required value from shared preferences, identified by given key.
     * Default value will be returned of no value found or error occurred.
     *
     * @param context      Context of caller activity
     * @param key          Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or any error occurs
     */
    public static String getFromPrefs(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPreferences.getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static Boolean getBoolean(Context context, Boolean isLoggedIn) {
        SharedPreferences boolPref = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return boolPref.getBoolean("isLoggedIn", false);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
