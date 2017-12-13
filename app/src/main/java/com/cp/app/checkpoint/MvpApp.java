package com.cp.app.checkpoint;

import android.app.Application;

import com.cp.app.checkpoint.data.DataManager;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public class MvpApp extends Application {
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        dataManager = new DataManager();

    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
