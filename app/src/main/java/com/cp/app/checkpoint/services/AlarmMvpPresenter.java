package com.cp.app.checkpoint.services;

import com.cp.app.checkpoint.ui.base.MvpPresenter;
import com.cp.app.checkpoint.ui.main.MainMvpView;

/**
 * Created by Ahmed Yehya on 29/12/2017.
 */

public interface AlarmMvpPresenter<V extends AlarmMvpView> extends MvpPresenter<V> {
    void clearTimer();

}
