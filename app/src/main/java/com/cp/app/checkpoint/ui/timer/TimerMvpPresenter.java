package com.cp.app.checkpoint.ui.timer;

import com.cp.app.checkpoint.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 28/12/2017.
 */

public interface TimerMvpPresenter <V extends TimerMvpView> extends MvpPresenter<V> {
    String getCounterDate();
}
