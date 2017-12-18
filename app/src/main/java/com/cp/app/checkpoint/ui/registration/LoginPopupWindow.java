package com.cp.app.checkpoint.ui.registration;

import android.view.View;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 14/12/2017.
 */

public interface LoginPopupWindow extends MvpView {
    void onLoginButtonClick();
    void initViewLoginPopup(View v);
}
