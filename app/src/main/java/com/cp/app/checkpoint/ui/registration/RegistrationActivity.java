package com.cp.app.checkpoint.ui.registration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.main.MainActivity;
import com.cp.app.checkpoint.utils.ValidationUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationActivity extends BaseActivity implements RegistrationMvpView,SignUpPopupWindow, LoginPopupWindow {

    RegistrationPresenter registrationPresenter;
    RelativeLayout relativeLayout;
    RelativeLayout mainLayout;
    Snackbar snackbar;
    // Activity buttons
    Button btnSignup;
    Button btnLoginInActivity;
    Button btnLoginLaterInActivity;

    PopupWindow popWindow;

    CallbackManager callbackManager;

    // Sign up
    Button btnConfirm,btnClose,btnLoginPopup,btnFacebookLogin;
    LoginButton btnFbLogin;
    TextView tvDateOfBirth;
    // Edit Text
    EditText etName, etMobileNum, etAddress, etPasswordSignup, etPasswordLogin, etPassLogin, etMobileNumLogin;
    // Strings
    String name, mobileNum, gender, dateOfBirth, address, passwordSignUpPopup, passwordLoginPopup;

    //Spinner
    private Spinner mGenderSpinner;
    private ArrayAdapter<String> genderSpinnerAdapter;

    final int DATE_DIALOG_ID = 999;

    int year;
    int month;
    int day;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        initViewActivity();

        final Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        callbackManager = CallbackManager.Factory.create();
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        registrationPresenter = new RegistrationPresenter(dataManager);

        registrationPresenter.onAttach(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPopu(view);
            }
        });

        btnLoginInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openLoginPopup(view);
                onLoginButtonClicked();
            }
        });

        btnLoginLaterInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.getStartIntent(RegistrationActivity.this));
            }
        });

        btnFbLogin.setReadPermissions("email");

        // Callback registration
        btnFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Toast.makeText(RegistrationActivity.this, loginResult.toString(), Toast.LENGTH_SHORT).show();

                final AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {






                        String name = user.optString("name");
                        String email = user.optString("email");
                        String id = user.optString("id");
                        String gender = user.optString("gender");
                        String birthday = user.optString("birthday");
                        String subId = id.substring(0,3);


                        if (name == null || name.equals(""))
                        {
                            Toast.makeText(RegistrationActivity.this,"name = null",Toast.LENGTH_SHORT);
                        }
                        else if (email == null || email.equals(""))
                        {
                            Toast.makeText(RegistrationActivity.this, "email = null", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {

                            Toast.makeText(RegistrationActivity.this, name + " : " + email + " : " + gender + " : " + birthday , Toast.LENGTH_SHORT).show();

                        }








                    }

                });



                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(RegistrationActivity.this, "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(RegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void openSignUpPopu(View v) {

        final PopupWindow popWindow;
        gender = getString(R.string.gender_no_selection);



        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

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
                dateOfBirth = tvDateOfBirth.getText().toString();
                address = etAddress.getText().toString();
                passwordSignUpPopup = etPasswordSignup.getText().toString();

                onConfirmButtonClick();

            }
        });

        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });







        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        //popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 400, true );

        // to set size as match_parent to fullscreen
        popWindow = new PopupWindow(inflatedView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }

        return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            tvDateOfBirth.setText(new StringBuilder().append(year)
                    .append("-").append(month+1).append("-").append(day));
        }
    };

    @Override
    public void openLoginPopup(View v) {



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

        //popWindow = new PopupWindow(inflatedView, size.x - 150,size.y - 600, true );

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
        startActivity(MainActivity.getStartIntent(this));
    }

    @Override
    public void onLoginButtonClicked() {
        boolean fieldsDone = true;

        String mobileNum = etMobileNumLogin.getText().toString();
        String password = etPassLogin.getText().toString();

        if (mobileNum.isEmpty() || password.isEmpty())
        {
            fieldsDone = false;
            Toast.makeText(this, R.string.toast_note_field_empty, Toast.LENGTH_SHORT).show();
        }
        else if (!(ValidationUtils.isMobileNumValid(mobileNum))) {
            fieldsDone = false;
            Toast.makeText(this, R.string.invalid_mobnum_signup, Toast.LENGTH_SHORT).show();
        }
        else if (fieldsDone)
        {
            registrationPresenter.startLogin(mobileNum,password);
        }

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
        btnSignup =  findViewById(R.id.btn_signup);
        btnLoginInActivity =  findViewById(R.id.btn_login);
        btnLoginLaterInActivity = findViewById(R.id.btn_login_later);
        btnFbLogin = findViewById(R.id.login_fb_button);
        etMobileNumLogin = findViewById(R.id.et_phone_number);
        etPassLogin = findViewById(R.id.et_password_login_popup);
        mainLayout = findViewById(R.id.main_reg_layout);
    }

    @Override
    public void toastLoginSuccess() {
        Toast.makeText(this,"login is done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shoSnackbarAlertWrongReg() {
        snackbar = Snackbar.make(relativeLayout, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void showSnackbarAlertexistedUser() {
        snackbar = Snackbar.make(relativeLayout, R.string.user_is_existed, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void showSnacbarUnknownErrorLogin() {
        snackbar = Snackbar.make(mainLayout,R.string.unknown_error,Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.try_again, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClicked();
            }
        });
        snackbar.show();
    }

    @Override
    public void showSnacbarPasswordIncorrectLogin() {
        snackbar = Snackbar.make(mainLayout,"Password is incorrect",Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void showSnackbarUserNotExisted() {
        snackbar = Snackbar.make(mainLayout, R.string.mob_num_not_registered,Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.register_now, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPopu(view);
            }
        });
        snackbar.show();
    }

    @Override
    public void onLoginButtonClick() {
         if (passwordLoginPopup.isEmpty())
             Toast.makeText(this, R.string.toast_empty_password_login_popup, Toast.LENGTH_SHORT).show();


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
        if (name.isEmpty() || mobileNum.isEmpty() || address.isEmpty() || passwordSignUpPopup.isEmpty()) {
            fieldsDone = false;
            Toast.makeText(this, R.string.toast_note_field_empty, Toast.LENGTH_SHORT).show();
        } else if (!(ValidationUtils.isMobileNumValid(mobileNum))) {
            fieldsDone = false;
            Toast.makeText(this, R.string.invalid_mobnum_signup, Toast.LENGTH_SHORT).show();
        } else if (!(ValidationUtils.isGenderValid(this, gender))) {
            fieldsDone = false;
            Toast.makeText(this, R.string.invalid_gender_signup, Toast.LENGTH_SHORT).show();
        } else if (!(ValidationUtils.isDateOfBirthValid(this, dateOfBirth))) {
            fieldsDone = false;
            Toast.makeText(this, R.string.invalid_birth_date, Toast.LENGTH_SHORT).show();
        } else if (fieldsDone)
        {
            registrationPresenter.startConfirm(name, mobileNum, gender, dateOfBirth, address, passwordSignUpPopup);
/*
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody body;
        okhttp3.Request request;

        String jsonRequest = "{\"name\":" + "\"" + name + "\"" + ",\"phone\":" + "\"" + mobileNum + "\"" + ",\"address\":" + "\"" + address + "\"" + ",\"password\":" + "\""+ passwordSignUpPopup+ "\"" + ",\"gender\":" + "\"" + gender + "\"" + ",\"birth_date\":" + "\"" + dateOfBirth + "\"" +"}";
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://checkpoint.cafe/register.php").newBuilder();
        urlBuilder.addQueryParameter("x", jsonRequest);
        String url = urlBuilder.build().toString();


        request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponce = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(stringResponce);
                    boolean error = jsonObject.getBoolean("error");
                    if(!error)
                    {
                        Integer intId = jsonObject.getInt("id");
                        String id = intId.toString();
                        registrationPresenter.saveUserData(id);

                        if (registrationPresenter.isLoggedIn())
                        {
                         startActivity(MainActivity.getStartIntent(RegistrationActivity.this));
                        }
                        else
                        {
                            popWindow.dismiss();
                            // TODO make snack bar to hint user to login
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        */
    }
    }

    @Override
    public void initViewSignUpPopup(View inflatedView) {
        // initialization views
        btnClose = (Button)inflatedView.findViewById(R.id.btn_close_signup_pop);
        btnConfirm = (Button)inflatedView.findViewById(R.id.btn_confirm_sign_up_popup);
        etName = (EditText)inflatedView. findViewById(R.id.et_name_sign_up_popup);
        etMobileNum = (EditText)inflatedView.findViewById(R.id.et_mobile_num_sign_up_popup);
        etAddress = (EditText)inflatedView. findViewById(R.id.et_address_sign_up_popup);
        etPasswordSignup = (EditText)inflatedView. findViewById(R.id.et_password_sign_up_popup);
        mGenderSpinner = (Spinner)inflatedView.findViewById(R.id.spinner_gender);
        tvDateOfBirth = inflatedView.findViewById(R.id.tx_birthday);
        relativeLayout = inflatedView.findViewById(R.id.popup_sign_up_layout);

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
