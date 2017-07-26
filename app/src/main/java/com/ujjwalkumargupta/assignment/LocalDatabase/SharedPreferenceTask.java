package com.ujjwalkumargupta.assignment.LocalDatabase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ujjwal Kumar Gupta on 26-Jul-17.
 */
public class SharedPreferenceTask {
    private static Context mContext;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public SharedPreferenceTask(Context ctx) {
        this.mContext = ctx;
    }

    //************** Put the String Values into Shared Preference ************//
    public static void putStringValueIntoSPref(String key, String value) {
        sharedPreferences = mContext.getSharedPreferences("MY_PREF", mContext.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
    }

    //************** Get the String Values into Shared Preference ************//
    public static String getStringValueFromSpref(String key) {
        sharedPreferences = mContext.getSharedPreferences("MY_PREF", mContext.MODE_PRIVATE);
        return sharedPreferences.getString(key, "default");
    }

    //************** Clearing Shared Preference All Values ***************//
    public static void clearSPref() {
        sharedPreferences = mContext.getSharedPreferences("MY_PREF", mContext.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().commit();
    }
}
