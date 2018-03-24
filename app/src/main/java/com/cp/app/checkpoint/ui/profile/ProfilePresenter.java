package com.cp.app.checkpoint.ui.profile;

import android.widget.Toast;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;
import com.cp.app.checkpoint.utils.StaticValues;
import com.cp.app.checkpoint.utils.ValidationUtils;

import org.json.JSONArray;
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
 * Created by Ahmed Yehya on 16/02/2018.
 */

public class ProfilePresenter <V extends ProfileMvpView> extends BasePresenter<V> implements ProfileMvpPresenter<V> {
    public ProfilePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void reqUserProfile() {
        if (getUserId() != null) {
            OkHttpClient client = new OkHttpClient();
            RequestBody body;

            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("user_id", getUserId())
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.GET_PROFILE_DATA)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().showSnackbar(R.string.connection_error, StaticValues.PROFILE_GET_DATA_ERROR_ID);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String stringResponse = response.body().string();

                    try {
                        JSONObject jsonResponse = new JSONObject(stringResponse);
                        Boolean status = jsonResponse.getBoolean("status");

                        if (status) {
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");

                            JSONObject jo = jsonArray.getJSONObject(0);
                            String name = jo.optString("name");
                            String phone = jo.optString("phone");
                            String dob = jo.optString("dop");
                            String address = jo.optString("address");
                            String password = jo.optString("password");
                            String points = jo.optString("points");

                            getMvpView().showUserData(points, name, phone, dob, address, password);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
            getMvpView().returnToRegistrationActivity();
    }

    @Override
    public String getUserId() {
        //TODO replace this
        return getDataManager().getUserId();
        // return "354";
    }

    @Override
    public void reqSaveProfileChanges(String name, String phone, String dob, String address, String password) {
        if (checkFields(name,phone,dob,address,password)) {
            OkHttpClient client = new OkHttpClient();
            RequestBody body;

            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("id", getUserId())
                    .addFormDataPart("name", name)
                    .addFormDataPart("address", address)
                    .addFormDataPart("dop", dob)
                    .addFormDataPart("password", password)
                    .addFormDataPart("phone", phone)
                    .build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.SAVE_PROFILE_DATA)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().showSnackbar(R.string.connection_error,StaticValues.PROFILE_EDIT_DATA_PROFILE);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String stringResponse = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(stringResponse);

                        boolean status = jsonObject.getBoolean("status");
                        if (status)
                        {
                            getMvpView().showToast(R.string.changes_saved);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void logout() {
        getDataManager().clearUserData();
        getMvpView().logoutToRegistrationActivity();
    }

    boolean checkFields(String name, String phone, String dob, String address, String password)
    {
        boolean fieldsDone = true;

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            fieldsDone = false;

            getMvpView().showToast(R.string.toast_note_field_empty);

        }else if (!(ValidationUtils.isMobileNumValid(phone))) {
            fieldsDone = false;
            getMvpView().showToast(R.string.invalid_mobnum_signup);

        }
        else if (dob.isEmpty()) {
            fieldsDone = false;
            getMvpView().showToast(R.string.invalid_birth_date);

        }

            return fieldsDone;
    }


}
