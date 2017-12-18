package com.cp.app.checkpoint.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainMvpView{

    MainPresenter mainPresenter;

    ArrayList<ListOfOneOrderModel> orderList;
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        mainPresenter = new MainPresenter(dataManager);
        mainPresenter.onAttach(this);

        orderList = new ArrayList<ListOfOneOrderModel>();
        this.orderList = mainPresenter.getOrderList();

        int l = orderList.size();
        System.out.println(l);
    }


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
