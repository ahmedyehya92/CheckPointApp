package com.cp.app.checkpoint.data;

import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 12/12/2017.
 */

public class DataManager {

    private ArrayList<ListOfOneOrderModel> orderListArray= new ArrayList<ListOfOneOrderModel>();

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

    }
    public void checkLoginUser(String password)
    {
        System.out.println(password);
    }

    // order list methods;
    public void addItemToList(String item_id, String itemName, Integer desiredQuantity)
    {
        orderListArray.add(new ListOfOneOrderModel(item_id,itemName,desiredQuantity));
    }

    public ArrayList<ListOfOneOrderModel> getOrderListArray ()
    {
        return orderListArray;
    }


}
