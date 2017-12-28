package com.cp.app.checkpoint.ui.listtoorder;

import android.net.Uri;

import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 20/12/2017.
 */

public interface ListToOrderMvpPresenter<V extends ListToOrderMvpView> extends MvpPresenter<V> {
    public ArrayList<ListOfOneOrderModel> getOrderList();
    public void deleteItemFromOrderList(Uri uri);
    public void setCounterDate(String date);
}
