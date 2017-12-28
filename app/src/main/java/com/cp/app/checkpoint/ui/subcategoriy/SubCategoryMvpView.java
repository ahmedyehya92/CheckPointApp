package com.cp.app.checkpoint.ui.subcategoriy;

import android.view.View;

import com.cp.app.checkpoint.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public interface SubCategoryMvpView extends MvpView {
void initViews();
void openAddToCartPopup(View v, final String itemId, final String itemName,final Integer priceOfOnePiece);
void onYourListButtonClick();
}
