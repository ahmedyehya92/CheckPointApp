package com.cp.app.checkpoint.ui.main;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.CategoryModel;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BasePresenter;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Ahmed Yehya on 17/12/2017.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter <V> implements MainMvpPresenter <V>{

    public MainPresenter(DataManager dataManager)
    {
        super(dataManager);
    }

    @Override
    public Integer getNumberOfItemsListOrder() {
        return getDataManager().getNumberOfItemList();
    }

    @Override
    public String getCounterDate() {
        return getDataManager().getDatetoCount();
    }

    @Override
    public void reqCategoriesList() {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.GET_CATEGORIES)
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
                    boolean error = jsonResponse.getBoolean("status");
                    ArrayList<CategoryModel> list = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray= jsonResponse.getJSONArray("Categories");

                    for (int i = 0; i<jsonArray.length();i++)
                    {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        CategoryModel categoryModel = new CategoryModel(jo.getString("id"),jo.getString("name"));
                        list.add(categoryModel);
                    }
                    getMvpView().hideProgressBar();
                    getMvpView().setupListView(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
