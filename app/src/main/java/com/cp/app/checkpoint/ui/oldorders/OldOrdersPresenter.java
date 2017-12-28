package com.cp.app.checkpoint.ui.oldorders;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public class OldOrdersPresenter <V extends OldOrdersMvpView> extends BasePresenter<V> implements OldOrdersMvpPresenter<V> {
    public OldOrdersPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
