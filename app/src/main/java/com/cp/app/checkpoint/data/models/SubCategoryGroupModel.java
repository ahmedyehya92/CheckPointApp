package com.cp.app.checkpoint.data.models;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 18/12/2017.
 */

public class SubCategoryGroupModel {
    private String name;
    private ArrayList<SubCategoryChildModel> itemsOfGroup;



    public SubCategoryGroupModel(String name, ArrayList<SubCategoryChildModel> itemsOfGroup) {
        this.name = name;

        this.itemsOfGroup = itemsOfGroup;
    }

    public ArrayList<SubCategoryChildModel> getItemsOfGroup() {
        return itemsOfGroup;
    }

    public String getName() {
        return name;
    }
}
