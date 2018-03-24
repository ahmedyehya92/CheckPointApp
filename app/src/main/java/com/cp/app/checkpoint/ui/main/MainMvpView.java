package com.cp.app.checkpoint.ui.main;

import android.widget.Button;
import android.widget.LinearLayout;

import com.cp.app.checkpoint.data.models.CategoryModel;
import com.cp.app.checkpoint.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 17/12/2017.
 */

public interface MainMvpView extends MvpView{

    void initViews();
    void onButtonYourListClicked();
    void setupListView(ArrayList<CategoryModel> list);
    void hideProgressBar();
    void showLayoutErrorConnection();

}
