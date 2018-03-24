package com.cp.app.checkpoint.data.models;

/**
 * Created by Ahmed Yehya on 03/01/2018.
 */

public class CategoryModel {
    private String categoryId;
    private String categoryName;

    public CategoryModel(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
