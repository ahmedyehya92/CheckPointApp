package com.cp.app.checkpoint.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahmed Yehya on 28/12/2017.
 */

public class SharedPrefHelper {
    public static final String MY_PREFS = "MY_PREFS";

    public static final String EMAIL = "EMAIL";

    public static final String DATE = "DATE";

    SharedPreferences mSharedPreferences;

    public SharedPrefHelper (Context context)
    {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    }

    public void clear()
    {
        mSharedPreferences.edit().clear().apply();
    }

    public void saveDate(String date)
    {
        mSharedPreferences.edit().putString(DATE,date).apply();
    }
    public String getDate()
    {
        return mSharedPreferences.getString(DATE,null);
    }
}
