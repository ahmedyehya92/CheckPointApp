package com.cp.app.checkpoint.ui.registration;

import android.view.View;
import android.widget.PopupWindow;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public interface RegistrationMvpView extends MvpView {

    void openSignUpPopu(View v);
    void openLoginPopup(View v);
    void openMainActivity();

    // popup sharable methods
    void onFacebookButtonClick();
    void onCloseButtonClick(PopupWindow popupWindow);


    void initViewActivity();

}
