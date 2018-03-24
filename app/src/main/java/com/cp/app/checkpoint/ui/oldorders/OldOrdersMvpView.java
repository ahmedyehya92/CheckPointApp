package com.cp.app.checkpoint.ui.oldorders;

import com.cp.app.checkpoint.data.models.OldOrderGroupModel;
import com.cp.app.checkpoint.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public interface OldOrdersMvpView extends MvpView {
    void initViews();
    void setupOldOrdersList(ArrayList<OldOrderGroupModel> list);
    void hideProgressBar();
    void showLayoutErrorConnection();
    void returnToRegistrationActivity();
    void showToast(String message);
}
