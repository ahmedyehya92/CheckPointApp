package com.cp.app.checkpoint.data.models;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public class SubCategoryChildModel {
    private Integer id;
    private String name;
    private Integer priceOfOnePiece;

    public SubCategoryChildModel(Integer id, String name, Integer priceOfOnePiece) {
        this.id = id;
        this.name = name;
        this.priceOfOnePiece = priceOfOnePiece;
    }

    public Integer getPriceOfOnePiece() {
        return priceOfOnePiece;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }
}
