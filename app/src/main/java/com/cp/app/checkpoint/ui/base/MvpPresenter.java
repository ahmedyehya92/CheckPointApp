package com.cp.app.checkpoint.ui.base;

/**
 * Created by Ahmed Yehya on 12/12/2017.
 */

public interface MvpPresenter<V extends MvpView>{

    void onAttach(V mvpView);

}
