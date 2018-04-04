package com.cp.app.checkpoint.ui.subcategoriy;

import com.cp.app.checkpoint.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public interface SubCategoryMvpPresenter<V extends SubCategoryMvpView> extends MvpPresenter<V> {
    void addItemToOrderList(String itemId, String itemName, String desiredQuantity, String totalPrice, String notes);
    Integer getNumberOfListItemsOrder();
    String getCounterDate();
    void reqSubCategories(String id);
}
