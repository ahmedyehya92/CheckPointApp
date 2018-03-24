package com.cp.app.checkpoint.ui.registration;

import android.view.View;
import android.widget.PopupWindow;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public class RegistrationPresenter<V extends RegistrationMvpView> extends BasePresenter <V> implements RegistrationMvpPresenter<V> {


    public RegistrationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startConfirm(String name, String mobileNo, String gender, String dateOfBirth, String address, String password) {
        registerNewUser(name,mobileNo,gender,dateOfBirth,address,password);


    }

    @Override
    public void saveUserData(String id) {
        getDataManager().saveUserData(id);
    }

    @Override
    public void startLogin(String mobileNum,String password) {
        //getMvpView().toastLoginSuccess();
        checkLogin(mobileNum,password);
    }

    @Override
    public boolean isLoggedIn() {
        return getDataManager().isLoggedIn();
    }

    public void registerNewUser (final String name, String mobileNo, String gender, String dateOfBirth, String address, String password) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body;

        body = new MultipartBody.Builder()
                .setType(FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("sex", gender)
                .addFormDataPart("phone", mobileNo)
                .addFormDataPart("address", address)
                .addFormDataPart("password", password)
                .addFormDataPart("dop", dateOfBirth)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.REGISTER_USER_URL)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(stringResponse);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error)
                    {
                        String userId = jsonObject.getString("user_id");
                        getDataManager().saveUserData(userId);
                        getMvpView().openMainActivity();

                    }
                    else
                    {
                        int errorCode = jsonObject.getInt("error_code");
                        if (errorCode == 101)
                            getMvpView().shoSnackbarAlertWrongReg();
                        else if (errorCode == 102)
                            getMvpView().showSnackbarAlertexistedUser();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkLogin (String phone, String password)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody body;

        body = new MultipartBody.Builder()
                .setType(FORM)
                .addFormDataPart("phone", phone)
                .addFormDataPart("password", password)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.LOGIN_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(stringResponse);
                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        String userId = jsonObject.getString("user_id");
                        getDataManager().saveUserData(userId);
                        getMvpView().openMainActivity();
                    }
                    else {
                        int errorCode = jsonObject.getInt("error_code");
                        if (errorCode == 101)
                        {
                            getMvpView().showSnacbarUnknownErrorLogin();
                        }
                        else if (errorCode == 102){
                            getMvpView().showSnacbarPasswordIncorrectLogin();
                        }
                        else if (errorCode == 103){
                            getMvpView().showSnackbarUserNotExisted();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
