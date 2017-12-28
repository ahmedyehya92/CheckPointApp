package com.cp.app.checkpoint.ui.listtoorder;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 20/12/2017.
 */

public interface ListToOrderMvpView extends MvpView {
    void initViewsActivity();
    void onOrderButtonClick();
    void setAlarm();
}
