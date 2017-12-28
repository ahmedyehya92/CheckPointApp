package com.cp.app.checkpoint.ui.listtoorder;

import android.net.Uri;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BasePresenter;

import java.util.ArrayList;

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
}
