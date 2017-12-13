package com.cp.app.checkpoint.ui.signup;

import android.view.View;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public interface SignUpMvpView extends MvpView {

    //Main
    void openSignUpPopu(View v);
    void openLoginPopup();
    void openMainActivity();

    //Sign Up Popup
    void onConfirmButtonClick();

    // popup sharable methods
    void onFacebookButtonClick();
    void onCloseButtonClick();

    //Login popup
    void onLoginButtonClick();


}
