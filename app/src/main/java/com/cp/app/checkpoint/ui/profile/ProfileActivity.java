package com.cp.app.checkpoint.ui.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.languagechanging.LanguageChangeActivity;
import com.cp.app.checkpoint.ui.main.MainActivity;
import com.cp.app.checkpoint.ui.oldorders.OldOrderActivity;
import com.cp.app.checkpoint.ui.registration.RegistrationActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import java.util.Calendar;

public class ProfileActivity extends BaseActivity implements ProfileMvpView {

    private ProfilePresenter profilePresenter;
    TextView tvPointsCount, tvDateOfBirth;
    EditText etName, etPhoneNumber, etAddress, etPassword;
    Button btnSave, btnLogout;
    final int DATE_DIALOG_ID = 999;
    int year, month, day;
    Snackbar snackbar;
    RelativeLayout relativeLayout;
    String name, phone, address, phoneNum, dateOfBirth;
    Calendar c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        profilePresenter = new ProfilePresenter(dataManager);
        profilePresenter.onAttach(this);

        profilePresenter.reqUserProfile();

        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String phone = etPhoneNumber.getText().toString();
                String address = etAddress.getText().toString();
                String password = etPassword.getText().toString();
                String dateOfBirth = tvDateOfBirth.getText().toString();

                onSaveButtonClick(name, phone, address, password, dateOfBirth);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogoutButtonClick();
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_language :
                startActivity(LanguageChangeActivity.getStartIntent(getApplicationContext()));
                return true;
            case R.id.item_old_order :
                startActivity(OldOrderActivity.getStartIntent(getApplicationContext()));
                return true;
            case R.id.item_profile :

        }
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }

        return super.onCreateDialog(id);    }

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
    public void initViews() {
        tvPointsCount = findViewById(R.id.tv_points_count);
        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        etAddress = findViewById(R.id.et_address);
        etPassword = findViewById(R.id.et_password);
        btnSave = findViewById(R.id.btn_save);
        btnLogout = findViewById(R.id.btn_logout);
        relativeLayout = findViewById(R.id.profile_layout);
    }

    @Override
    public void showUserData(final String pointCount, final String Name, final String phoneNumber, final String dateOfBirth, final String address, final String password) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!pointCount.equals("null") )
                    tvPointsCount.setText(pointCount);

                if (!Name.equals("null"))
                    etName.setText(Name);

                if (!phoneNumber.equals("null"))
                    etPhoneNumber.setText(phoneNumber);
                if (!dateOfBirth.equals("null"))
                    tvDateOfBirth.setText(dateOfBirth);

                if (!address.equals("null"))
                    etAddress.setText(address);

                if (!password.equals("null"))
                    etPassword.setText(password);
            }
        });
    }

    @Override
    public void onSaveButtonClick(String name,String phone,String address,String password,String dateOfBirth) {
        profilePresenter.reqSaveProfileChanges(name, phone, dateOfBirth, address, password);
    }

    @Override
    public void showToast(int message) {
        final String mesg = getString(message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileActivity.this, mesg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showSnackbar(int message, int errorType) {
        switch (errorType) {
            case StaticValues.PROFILE_GET_DATA_ERROR_ID :
                snackbar = Snackbar.make(relativeLayout,message,Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        profilePresenter.reqUserProfile();
                    }
                });
                snackbar.show();
                break;
            case StaticValues.PROFILE_EDIT_DATA_PROFILE:
                snackbar = Snackbar.make(relativeLayout,message,Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = etName.getText().toString();
                        String phone = etPhoneNumber.getText().toString();
                        String address = etAddress.getText().toString();
                        String password = etPassword.getText().toString();
                        String dateOfBirth = tvDateOfBirth.getText().toString();

                        onSaveButtonClick(name, phone, address, password, dateOfBirth);
                    }
                });
                snackbar.show();
                break;
        }

    }

    @Override
    public void returnToRegistrationActivity() {
        startActivity(RegistrationActivity.getStartIntent(this));
        finish();
        Toast.makeText(this, R.string.signup_to_con, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogoutButtonClick() {
        profilePresenter.logout();
    }

    @Override
    public void logoutToRegistrationActivity() {
        startActivity(RegistrationActivity.getStartIntent(this));
        finish();
        Toast.makeText(this, R.string.toast_logged_out, Toast.LENGTH_SHORT).show();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }
}
