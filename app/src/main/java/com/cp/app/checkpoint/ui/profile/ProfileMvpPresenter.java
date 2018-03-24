package com.cp.app.checkpoint.ui.profile;

import com.cp.app.checkpoint.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 16/02/2018.
 */

public interface ProfileMvpPresenter <V extends ProfileMvpView> extends MvpPresenter<V> {
    void reqUserProfile();
    String getUserId();
    void reqSaveProfileChanges(String name, String phone, String dob, String address, String password);
    void logout();
}
