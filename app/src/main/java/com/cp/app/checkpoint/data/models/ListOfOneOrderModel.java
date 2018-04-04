package com.cp.app.checkpoint.data.models;

/**
 * Created by Ahmed Yehya on 17/12/2017.
 */

public class ListOfOneOrderModel {
    private String itemId;
    private String itemName;
    private String dbId;
    private Integer desiredQuantity;
    private Integer priceForDesiredQuantity;
    private String notes;




    public ListOfOneOrderModel(String dbId, String itemId, String itemnName, Integer desiredqQuantity, Integer priceForDesiredQuantity) {
        this.itemId = itemId;
        this.itemName = itemnName;
        this.desiredQuantity = desiredqQuantity;
        this.priceForDesiredQuantity = priceForDesiredQuantity;
        this.dbId = dbId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDbId() {
        return dbId;
    }

    public Integer getPriceForDesiredQuantity() {
        return priceForDesiredQuantity;
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

    public String getNotes() {
        return notes;
    }
}
