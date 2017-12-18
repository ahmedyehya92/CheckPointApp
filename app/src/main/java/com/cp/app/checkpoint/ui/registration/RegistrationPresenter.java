package com.cp.app.checkpoint.ui.registration;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 13/12/2017.
 */

public class RegistrationPresenter<V extends RegistrationMvpView> extends BasePresenter <V> implements RegistrationMvpPresenter<V> {

    public RegistrationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startConfirm(String name, String mobileNo, String gender, String dateOfBirth, String address, String password) {
        getDataManager().uploadUserData(name,mobileNo, gender, dateOfBirth, address, password);
    }

    @Override
    public void saveUserData(String id) {
        getDataManager().saveUserData(id);

    }

    @Override
    public void startLogin(String password) {
        getDataManager().checkLoginUser(password);
    }

    @Override
    public void addItemToOrderList(String itemId, String itemName, Integer desiredQuantity) {
        getDataManager().addItemToList(itemId,itemName,desiredQuantity);
    }
}
