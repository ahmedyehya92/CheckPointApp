package com.cp.app.checkpoint.ui.registration;

import android.view.View;
import android.widget.Button;

import com.cp.app.checkpoint.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Yehya on 14/12/2017.
 */

public interface SignUpPopupWindow extends MvpView {

    void onConfirmButtonClick();
    void initViewSignUpPopup(View v);
}
