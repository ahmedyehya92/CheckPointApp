package com.cp.app.checkpoint.ui.profile;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 16/02/2018.
 */

public interface ProfileMvpView extends MvpView {
    void initViews();
    void showUserData(String pointCount, String Name, String phoneNumber, String dateOfBirth, String address, String password);
    void onSaveButtonClick(String name,String phone,String address,String password,String dateOfBirth);
    void showToast (int message);
    void showSnackbar(int message, int errorType);
    void returnToRegistrationActivity();
    void onLogoutButtonClick();
    void logoutToRegistrationActivity();
}
