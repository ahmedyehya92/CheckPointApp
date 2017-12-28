package com.cp.app.checkpoint.ui.timer;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 28/12/2017.
 */

public class TimerPresenter <V extends TimerMvpView> extends BasePresenter<V> implements TimerMvpPresenter<V> {
    public TimerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public String getCounterDate() {
       return getDataManager().getDatetoCount();
    }
}
