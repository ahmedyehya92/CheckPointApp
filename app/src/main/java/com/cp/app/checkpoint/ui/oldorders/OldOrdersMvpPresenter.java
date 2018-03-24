package com.cp.app.checkpoint.ui.oldorders;

import com.cp.app.checkpoint.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public interface OldOrdersMvpPresenter <V extends OldOrdersMvpView> extends MvpPresenter<V> {
    void reqOldOrders();
    String getUserId();
}
