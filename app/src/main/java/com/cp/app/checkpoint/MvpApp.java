package com.cp.app.checkpoint;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.SharedPrefHelper;
import com.cp.app.checkpoint.data.dphelper.SQLiteHandler;
import com.cp.app.checkpoint.ui.main.MainActivity;

import java.util.Locale;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public class MvpApp extends Application {
    DataManager dataManager;


    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteHandler sqLiteHandler = new SQLiteHandler(getApplicationContext());
        SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(getApplicationContext());
        dataManager = new DataManager(sqLiteHandler,sharedPrefHelper);
        //setLocale("ar");

    }

    public DataManager getDataManager() {
        return dataManager;
    }







    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLayoutDirection(myLocale);
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}
