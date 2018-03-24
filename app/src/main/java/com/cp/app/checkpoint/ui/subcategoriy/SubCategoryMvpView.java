package com.cp.app.checkpoint.ui.subcategoriy;

import android.view.View;

import com.cp.app.checkpoint.data.models.SubCategoryGroupModel;
import com.cp.app.checkpoint.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public interface SubCategoryMvpView extends MvpView {
void initViews();
void openAddToCartPopup(View v, final String itemId, final String itemName,final Integer priceOfOnePiece);
void onYourListButtonClick();
void setupSubCategoryList(ArrayList<SubCategoryGroupModel> list);
void hideProgressBar();
void showErrorConnectionLayout();
void showToast(int msg);
}
