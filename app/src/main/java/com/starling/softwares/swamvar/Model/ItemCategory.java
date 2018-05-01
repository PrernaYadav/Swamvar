package com.starling.softwares.swamvar.Model;

/**
 * Created by Admin on 1/4/2018.
 */

public class ItemCategory {

    private int catergoryId;
    private String categoryName;

    public ItemCategory(int catergoryId, String categoryName) {
        this.catergoryId = catergoryId;
        this.categoryName = categoryName;
    }

    public int getCatergoryId() {
        return catergoryId;
    }

    public void setCatergoryId(int catergoryId) {
        this.catergoryId = catergoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
