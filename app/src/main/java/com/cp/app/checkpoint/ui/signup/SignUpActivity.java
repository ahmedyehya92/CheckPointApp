package com.cp.app.checkpoint.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity implements SignUpMvpView {

    SignUpPresenter signUpPresenter;

    Button btnSignup;
    @BindView(R.id.btn_login)Button btnLogin;
    @BindView(R.id.btn_login_later)Button btnLoginLater;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        signUpPresenter = new SignUpPresenter(dataManager);

        signUpPresenter.onAttach(this);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPopu(view);
            }
        });
    }

    @Override
    public void openSignUpPopu(View v) {

        PopupWindow popWindow;
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.ppoup_signup, null,false);

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

// to set size as match_parent to fullscreen
        popWindow = new PopupWindow(inflatedView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_signup_popup));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // set animation for appear and disappear

        //popWindow.setAnimationStyle(R.style.AnimationPopup);


        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.CENTER, 0,0);
    }

    @Override
    public void openLoginPopup() {

    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void onConfirmButtonClick() {

    }

    @Override
    public void onFacebookButtonClick() {

    }

    @Override
    public void onCloseButtonClick() {

    }

    @Override
    public void onLoginButtonClick() {

    }




}
