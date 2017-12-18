package com.cp.app.checkpoint.data.models;

/**
 * Created by Ahmed Yehya on 17/12/2017.
 */

public class ListOfOneOrderModel {
    private String itemId;
    private String itemName;
    private Integer desiredQuantity;

    public ListOfOneOrderModel(String itemId, String itemnName, Integer desiredqQuantity) {
        this.itemId = itemId;
        this.itemName = itemnName;
        this.desiredQuantity = desiredqQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDesiredqQuantity() {
        return desiredQuantity.toString();
    }
}
