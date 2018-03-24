package com.cp.app.checkpoint.services;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 29/12/2017.
 */

public class AlarmPresenter <V extends AlarmMvpView> extends BasePresenter<V> implements AlarmMvpPresenter<V> {
    public AlarmPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void clearTimer() {
        getDataManager().clearTimer();
    }
}
