package com.cp.app.checkpoint.data;

import android.net.Uri;

import com.cp.app.checkpoint.data.dphelper.SQLiteHandler;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 12/12/2017.
 */

public class DataManager {

    private SQLiteHandler mSqliteHandler;

    SharedPrefHelper sharedPrefHelper;


    public DataManager(SQLiteHandler mSqliteHandler, SharedPrefHelper sharedPrefHelper1)
    {
        this.mSqliteHandler = mSqliteHandler;
        this.sharedPrefHelper = sharedPrefHelper1;
    }

    public void clearShPref()
    {
        sharedPrefHelper.clear();
    }
    public void clearTimer()
    {
        sharedPrefHelper.clearTimer();
    }
    public void saveDateForCount(String date)
    {
        sharedPrefHelper.saveDate(date);
    }
    public String getDatetoCount()
    {
        return sharedPrefHelper.getDate();
    }

    public void addItemToOrderList(String itemId, String itemName, String desiredQuantity, String totalPrice, String notes) {
        mSqliteHandler.addItemToOrderList(itemId,itemName,desiredQuantity,totalPrice,notes);
    }
    public ArrayList<ListOfOneOrderModel> getOrderItemList()
    {
        return mSqliteHandler.getOrderItemList();
    }

    public Integer getNumberOfItemList ()
    {
        return mSqliteHandler.getOrderItemList().size();
    }

    public void deleteItemFromOrderList(Uri uri)
    {
        mSqliteHandler.deleteItemFromOrderList(uri);
    }

    public void uploadUserData(String name, String mobileNo, String gender, String dateOfBirth, String address, String password) {
       System.out.println(name);
        System.out.println(mobileNo);
        System.out.println(gender);
        System.out.println(dateOfBirth);
        System.out.println(address);
        System.out.println(password);
    }
    public void saveUserData (String id)
    {
        sharedPrefHelper.setId(id);
        sharedPrefHelper.setLogin(true);
    }

    public void clearUserData ()
    {
        sharedPrefHelper.setLogin(false);
        sharedPrefHelper.setId(null);
    }

    public boolean isLoggedIn()
    {
        return sharedPrefHelper.isLoggedIn();
    }
    public void checkLoginUser(String password)
    {
        System.out.println(password);
    }

    public String getUserId()
    {
        return sharedPrefHelper.getId();
    }

    // order list methods;


    public void setAppLanguage(String appLanguage)
    {
        sharedPrefHelper.setAppLanguage(appLanguage);
    }
    public String getAppLanguage ()
    {
        return sharedPrefHelper.getAppLanguage();
    }


}
