package com.cp.app.checkpoint.ui.signup;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public class SignUpPresenter <V extends SignUpMvpView> extends BasePresenter <V> implements  SignUpMvpPresenter<V>{
    public SignUpPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
