package com.cp.app.checkpoint.data.models;

/**
 * Created by Ahmed Yehya on 24/12/2017.
 */

public class OldOrderChildModel {
    private String name;
    private Integer quantity;

    public OldOrderChildModel(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity.toString();
    }
}
