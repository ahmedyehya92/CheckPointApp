package com.cp.app.checkpoint.data.models;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public class SubCategoryChildModel {
    private Integer id;
    private String name;

    public SubCategoryChildModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }
}
