package com.cp.app.checkpoint.ui.listtoorder;

import android.net.Uri;
import android.util.Log;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.dphelper.ItemContract;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BasePresenter;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

/**
 * Created by Ahmed Yehya on 20/12/2017.
 */

public class ListToOrderPresenter<V extends ListToOrderMvpView> extends BasePresenter<V> implements ListToOrderMvpPresenter<V> {
    public ListToOrderPresenter(DataManager dataManager)
    {
        super(dataManager);
    }

    @Override
    public ArrayList<ListOfOneOrderModel> getOrderList() {
        return getDataManager().getOrderItemList();
    }

    @Override
    public void deleteItemFromOrderList(Uri uri) {
        getDataManager().deleteItemFromOrderList(uri);
    }

    @Override
    public void setCounterDate(String date) {
        getDataManager().saveDateForCount(date);
    }

    @Override
    public void sendOrder() {
        if (getUserId() != null) {
            OkHttpClient client = new OkHttpClient();
            RequestBody body;



            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("json", getJsonString())
                    .build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.SEND_ORDER_URL)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().showToast(R.string.check_your_connection_or_try_again);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String stringResponse = response.body().string();
                    Log.d("any", "onResponse: "+ stringResponse);

                    try {
                        JSONObject jsonResponse = new JSONObject(stringResponse);
                        Boolean status = jsonResponse.getBoolean("status");
                        if (status) {
                            String newBonus = jsonResponse.getString("points");
                            String score = jsonResponse.getString("new_points after add");

                            deleteItemFromOrderList(ItemContract.ItemEntry.CONTENT_URI);
                            getMvpView().showNewBonusAlert(newBonus, score);
                            getMvpView().setAlarm();
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

    String getJsonString ()
    {
        JSONObject jsonObject = new JSONObject();
        JSONArray itemsArray = new JSONArray();

        Integer intTotalPrice = new Integer(0);
        ArrayList<ListOfOneOrderModel> listOfOrder = getOrderList();
        //Log.d("ListToOrder", "getJsonString: " + listOfOrder.get(0).getNotes());
        for (int i =0; i<listOfOrder.size(); i++)
        {
            int desiredQuantity = Integer.parseInt(listOfOrder.get(i).getDesiredqQuantity());
            int priceOfDesired = listOfOrder.get(i).getPriceForDesiredQuantity();
            int unitPrice = priceOfDesired/desiredQuantity;
            intTotalPrice += listOfOrder.get(i).getPriceForDesiredQuantity().intValue();
            try {
                itemsArray.put(new JSONObject().put("id",listOfOrder.get(i).getItemId())
                        .put("quantity",listOfOrder.get(i).getDesiredqQuantity())
                        .put("price",listOfOrder.get(i).getPriceForDesiredQuantity())
                        .put("unit_price", unitPrice)
                        .put("note", listOfOrder.get(i).getNotes())
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
       /* long ms = System.currentTimeMillis();
        Date date = new Date(ms);
        SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        String t = dateformat.format(date);
        System.out.println(t); */
        try {
            jsonObject.put("user_id", getUserId())
                    .put("total_price", intTotalPrice.toString())
                    .put("point","5")
                    .put("items", itemsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public String getUserId() {
        //TODO replace this
        return getDataManager().getUserId();
        //return "354";
    }
}
