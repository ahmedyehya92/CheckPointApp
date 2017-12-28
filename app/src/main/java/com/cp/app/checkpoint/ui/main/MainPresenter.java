package com.cp.app.checkpoint.ui.main;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BasePresenter;

import java.util.ArrayList;

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
}
