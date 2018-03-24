package com.cp.app.checkpoint.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ahmed Yehya on 28/12/2017.
 */

public class SharedPrefHelper {

    final String TAG = SharedPreferences.class.getSimpleName();

    public static final String MY_PREFS = "MY_PREFS";

    public static final String ID = "ID";
    public static final String APP_LANGUAGE = "app_lang";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
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

    public void clearTimer()
    {
        mSharedPreferences.edit().putString(DATE,null).apply();
    }

    public String getDate()
    {
        return mSharedPreferences.getString(DATE,null);
    }

    public void setLogin(boolean isLoggedIn) {
        mSharedPreferences.edit().putBoolean(KEY_IS_LOGGEDIN, isLoggedIn).apply();
        Log.i(TAG, "login status is" + isLoggedIn());
    }
    public boolean isLoggedIn(){
        return mSharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setId (String id)
    {
        mSharedPreferences.edit().putString(ID, id).apply();
        Log.i(TAG,"id " + id + " has been added");
    }

    public String getId ()
    {
        return mSharedPreferences.getString(ID , null);
    }

    public void setAppLanguage(String language)
    {
        mSharedPreferences.edit().putString(APP_LANGUAGE, language).apply();
    }
    public  String getAppLanguage()
    {
        return mSharedPreferences.getString(APP_LANGUAGE,"en");
    }

    public void clearId ()
    {
        mSharedPreferences.edit().putString(ID, null).apply();
    }
}
