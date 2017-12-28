package com.cp.app.checkpoint.ui.subcategoriy;

import android.view.View;
import android.widget.PopupWindow;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 20/12/2017.
 */

public interface PopupAddToCart extends MvpView {
    void initPopubVies(View v);
    void onAddToCartButtonClick(PopupWindow popupWindow, String userId, String itemId, String itemName, Integer quantity, Integer totalPrice);
}
