package com.cp.app.checkpoint.ui.registration;

import com.cp.app.checkpoint.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public interface RegistrationMvpPresenter<V extends RegistrationMvpView> extends MvpPresenter<V> {
   void startConfirm(String name, String mobileNo, String gender, String dateOfBirth, String address, String password);
   void saveUserData(String id);
   void startLogin(String password);
   void addItemToOrderList(String itemId, String itemName, Integer desiredQuantity);

}
