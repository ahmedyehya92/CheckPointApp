package com.cp.app.checkpoint.ui.main;

import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 17/12/2017.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    ArrayList<ListOfOneOrderModel> getOrderList();
}
