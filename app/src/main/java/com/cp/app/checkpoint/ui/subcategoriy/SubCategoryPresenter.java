package com.cp.app.checkpoint.ui.subcategoriy;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.SubCategoryChildModel;
import com.cp.app.checkpoint.data.models.SubCategoryGroupModel;
import com.cp.app.checkpoint.ui.base.BasePresenter;
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
 * Created by Ahmed Yehya on 18/12/2017.
 */

public class SubCategoryPresenter<V extends SubCategoryMvpView> extends BasePresenter<V> implements SubCategoryMvpPresenter<V> {
    public SubCategoryPresenter(DataManager dataManager)
    {
        super(dataManager);
    }
    @Override
    public void addItemToOrderList(String itemId, String itemName, String desiredQuantity, String totalPrice) {
        getDataManager().addItemToOrderList(itemId,itemName,desiredQuantity,totalPrice);
    }

    @Override
    public Integer getNumberOfListItemsOrder() {
        return getDataManager().getNumberOfItemList();
    }

    @Override
    public String getCounterDate() {
        return getDataManager().getDatetoCount();
    }

    @Override
    public void reqSubCategories(String id) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body;

        body = new MultipartBody.Builder()
                .setType(FORM)
                .addFormDataPart("id", id)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.GET_SUBCATEGORIES)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().hideProgressBar();
                getMvpView().showErrorConnectionLayout();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();

                try {
                    JSONObject jsonResponse = new JSONObject(stringResponse);

                    Boolean status = jsonResponse.getBoolean("status");
                    if (status)
                    {
                        JSONObject subCatObject = jsonResponse.getJSONObject("sub");

                        ArrayList<SubCategoryGroupModel> list = new ArrayList<SubCategoryGroupModel>();
                        ArrayList<SubCategoryChildModel> ch_list;

                        Iterator<String> key = subCatObject.keys();

                        while (key.hasNext()) {
                            String k = key.next();
                            SubCategoryGroupModel productsGroup;
                            ch_list = new ArrayList<SubCategoryChildModel>();
                            JSONArray ja = subCatObject.getJSONArray(k);

                            for (int i = 0; i<ja.length();i++)
                            {
                                JSONObject jo = ja.getJSONObject(i);
                                SubCategoryChildModel ch = new SubCategoryChildModel(jo.getInt("id"),jo.getString("name"),jo.getInt("price"));
                                ch_list.add(ch);
                            }
                            SubCategoryGroupModel gru = new SubCategoryGroupModel(k,ch_list);
                            list.add(gru);
                        }
                        getMvpView().hideProgressBar();
                        getMvpView().setupSubCategoryList(list);
                    }
                    else
                    {
                        getMvpView().hideProgressBar();
                        getMvpView().showToast(R.string.no_items);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
