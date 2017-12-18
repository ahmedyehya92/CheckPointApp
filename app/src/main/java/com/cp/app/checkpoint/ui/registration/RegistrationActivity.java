package com.cp.app.checkpoint.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.main.MainActivity;
import com.cp.app.checkpoint.utils.ValidationUtils;

public class RegistrationActivity extends BaseActivity implements RegistrationMvpView,SignUpPopupWindow, LoginPopupWindow {

    RegistrationPresenter registrationPresenter;
    // Activity buttons
    Button btnSignup;
    Button btnLoginInActivity;
    Button btnLoginLaterInActivity;

    // Sign up
    Button btnConfirm,btnFacebookRegistration,btnClose,btnLoginPopup,btnFacebookLogin;
    // Edit Text
    EditText etName, etMobileNum, etAddress, etPasswordSignup, etPasswordLogin;
    // Strings
    String name, mobileNum, gender, dateOfBirth, address, passwordSignUpPopup, passwordLoginPopup;

    //Spinner
    private Spinner mGenderSpinner;
    private ArrayAdapter<String> genderSpinnerAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViewActivity();


        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        registrationPresenter = new RegistrationPresenter(dataManager);

        registrationPresenter.onAttach(this);
        registrationPresenter.addItemToOrderList("5","Tea",5);
        registrationPresenter.addItemToOrderList("3","cofee",6);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPopu(view);
            }
        });

        btnLoginInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPopup(view);
            }
        });

        btnLoginLaterInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.getStartIntent(RegistrationActivity.this));
            }
        });

    }

    @Override
    public void openSignUpPopu(View v) {

        final PopupWindow popWindow;
        gender = getString(R.string.gender_no_selection);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.ppoup_signup, null, false);

       initViewSignUpPopup(inflatedView);

        //Spinner initialization
        String [] genderArray = {getString(R.string.gender_no_selection),getString(R.string.gender_male),getString(R.string.gender_female)};
        genderSpinnerAdapter = new ArrayAdapter<>(this,
                R.layout.ginder_spinner_row, R.id.gender,genderArray);
        genderSpinnerAdapter.setDropDownViewResource(R.layout.ginder_spinner_dropdown_row);

        setupSpinner();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                mobileNum = etMobileNum.getText().toString();
                dateOfBirth = "18/5/92";
                address = etAddress.getText().toString();
                passwordSignUpPopup = etPasswordSignup.getText().toString();

                onConfirmButtonClick();

            }
        });

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 400, true );

        // to set size as match_parent to fullscreen
        //popWindow = new PopupWindow(inflatedView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_signup_popup));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // set animation for appear and disappear

        //popWindow.setAnimationStyle(R.style.AnimationPopup);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCloseButtonClick(popWindow);
            }
        });
    }

    @Override
    public void openLoginPopup(View v) {

        final PopupWindow popWindow;

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.popup_login, null, false);

        initViewLoginPopup(inflatedView);

       btnLoginPopup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               passwordLoginPopup = etPasswordLogin.getText().toString();
               onLoginButtonClick();
           }
       });


        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        //popWindow = new PopupWindow(inflatedView, size.x - 150,size.y - 700, true );

        // to set size as match_parent to fullscreen
        popWindow = new PopupWindow(inflatedView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_signup_popup));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // set animation for appear and disappear

        //popWindow.setAnimationStyle(R.style.AnimationPopup);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCloseButtonClick(popWindow);
            }
        });
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void onFacebookButtonClick() {

    }

    @Override
    public void onCloseButtonClick(PopupWindow popupWindow) {
        popupWindow.dismiss();
    }

    @Override
    public void initViewActivity() {
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLoginInActivity = (Button) findViewById(R.id.btn_login);
        btnLoginLaterInActivity = (Button) findViewById(R.id.btn_login_later);
    }

    @Override
    public void onLoginButtonClick() {
         if (passwordLoginPopup.isEmpty())
             Toast.makeText(this, R.string.toast_empty_password_login_popup, Toast.LENGTH_SHORT).show();
         else
           registrationPresenter.startLogin(passwordLoginPopup);
    }

    @Override
    public void initViewLoginPopup(View inflatedView) {
        btnClose = (Button)inflatedView.findViewById(R.id.btn_close_login_pop);
        btnLoginPopup = (Button)inflatedView.findViewById(R.id.btn_login_login_popup);
        btnFacebookLogin = (Button)inflatedView.findViewById(R.id.btn_facebook_login);

        etPasswordLogin = (EditText)inflatedView.findViewById(R.id.et_password_login_popup);
    }

    @Override
    public void onConfirmButtonClick() {
        boolean fieldsDone = true;
        if (name.isEmpty()||mobileNum.isEmpty()||address.isEmpty()|| passwordSignUpPopup.isEmpty()) {
            fieldsDone = false;
            Toast.makeText(this, R.string.toast_note_field_empty, Toast.LENGTH_SHORT).show();
        }
        else if (!(ValidationUtils.isMobileNumValid(mobileNum)))
        {
            fieldsDone = false;
            Toast.makeText(this, R.string.invalid_mobnum_signup, Toast.LENGTH_SHORT).show();
        }
        else if(!(ValidationUtils.isGenderValid(this,gender)))
        {
            fieldsDone = false;
            Toast.makeText(this, R.string.invalid_gender_signup, Toast.LENGTH_SHORT).show();
        }
        
        else if(fieldsDone)
        registrationPresenter.startConfirm(name,mobileNum,gender,dateOfBirth,address, passwordSignUpPopup);
    }

    @Override
    public void initViewSignUpPopup(View inflatedView) {
        // initialization views
        btnClose = (Button)inflatedView.findViewById(R.id.btn_close_signup_pop);
        btnConfirm = (Button)inflatedView.findViewById(R.id.btn_confirm_sign_up_popup);
        btnFacebookRegistration = (Button)inflatedView. findViewById(R.id.btn_facebook_reg);

        etName = (EditText)inflatedView. findViewById(R.id.et_name_sign_up_popup);
        etMobileNum = (EditText)inflatedView.findViewById(R.id.et_mobile_num_sign_up_popup);
        etAddress = (EditText)inflatedView. findViewById(R.id.et_address_sign_up_popup);
        etPasswordSignup = (EditText)inflatedView. findViewById(R.id.et_password_sign_up_popup);
        mGenderSpinner = (Spinner)inflatedView.findViewById(R.id.spinner_gender);
    }

    private void setupSpinner() {

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        gender = getString(R.string.gender_male); // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        gender = getString(R.string.gender_female); // Female
                    }
                    else
                        gender = getString(R.string.gender_no_selection);
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = getString(R.string.gender_no_selection); // Unknown
            }
        });
    }
}
