package com.cp.app.checkpoint.ui.oldorders;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.OldOrderChildModel;
import com.cp.app.checkpoint.data.models.OldOrderGroupModel;
import com.cp.app.checkpoint.ui.base.BasePresenter;
import com.cp.app.checkpoint.ui.registration.RegistrationActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public class OldOrdersPresenter <V extends OldOrdersMvpView> extends BasePresenter<V> implements OldOrdersMvpPresenter<V> {
    public OldOrdersPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void reqOldOrders() {
        if (getUserId() != null)
        {
            OkHttpClient client = new OkHttpClient();
        RequestBody body;

        body = new MultipartBody.Builder()
                .setType(FORM)
                .addFormDataPart("id", getUserId())
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.GET_OLDORDERS)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().hideProgressBar();
                getMvpView().showLayoutErrorConnection();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();

                try {
                    JSONObject jsonResponse = new JSONObject(stringResponse);

                    Boolean status = jsonResponse.getBoolean("status");

                    if (status) {
                        JSONObject mainListObject = jsonResponse.getJSONObject("data");
                        ArrayList<OldOrderGroupModel> list = new ArrayList<OldOrderGroupModel>();
                        ArrayList<OldOrderChildModel> ch_list;

                        Iterator<String> key = mainListObject.keys();

                        while (key.hasNext()) {
                            String k = key.next();
                            OldOrderGroupModel itemGroup;
                            ch_list = new ArrayList<OldOrderChildModel>();
                            JSONArray ja = mainListObject.getJSONArray(k);
                            String totalPrice = null;
                            String firTotlaPrice = null;
                            String finalPrice = null;
                            for (int i = 0; i < ja.length(); i++) {
                                if (i == 0) {
                                    JSONObject priceObject = ja.getJSONObject(i);
                                    totalPrice = priceObject.getString("total");
                                    firTotlaPrice = totalPrice.substring(0, totalPrice.length() - 1);
                                    finalPrice = firTotlaPrice.substring(1);
                                } else {
                                    JSONObject jo = ja.getJSONObject(i);
                                    OldOrderChildModel ch = new OldOrderChildModel(jo.getString("name"), jo.getInt("quantity"));
                                    ch_list.add(ch);
                                }
                            }
                            OldOrderGroupModel gru = new OldOrderGroupModel(k, ch_list);
                            gru.setTotalPriceForOrder(totalPrice);
                            list.add(gru);
                        }
                        getMvpView().hideProgressBar();
                        getMvpView().setupOldOrdersList(list);

                    }
                    else {
                        String message = jsonResponse.getString("message");
                        getMvpView().hideProgressBar();
                        getMvpView().showToast(message);
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
        //return "354";
    }

}
