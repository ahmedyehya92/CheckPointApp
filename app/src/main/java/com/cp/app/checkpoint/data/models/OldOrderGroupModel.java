package com.cp.app.checkpoint.data.models;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public class OldOrderGroupModel {
    private String title;
    private String totalPriceForOrder;
    private ArrayList<OldOrderChildModel> itemsOfGroup;

    public OldOrderGroupModel(String title, ArrayList<OldOrderChildModel> itemsOfGroup) {
        this.title = title;
        this.itemsOfGroup = itemsOfGroup;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<OldOrderChildModel> getItemsOfGroup() {
        return itemsOfGroup;
    }

    public void setTotalPriceForOrder(String totalPriceForOrder) {
        this.totalPriceForOrder = totalPriceForOrder;
    }

    public String getTotalPriceForOrder() {
        return totalPriceForOrder;
    }
}
