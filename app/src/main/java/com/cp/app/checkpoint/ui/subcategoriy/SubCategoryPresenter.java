package com.cp.app.checkpoint.ui.subcategoriy;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public class SubCategoryPresenter<V extends SubCategoryMvpView> extends BasePresenter<V> implements SubCategoryMvpPresenter<V> {
    public SubCategoryPresenter(DataManager dataManager)
    {
        super(dataManager);
    }

}
