package com.oteusite.aplicaodecomprasandroid.model;

import androidx.appcompat.app.AppCompatActivity;

public class AllCategoryModel extends AppCompatActivity {

    Integer id;
    Integer imageurl;
    String categoryName;

    public AllCategoryModel(Integer id, Integer imageurl, String categoryName) {
        this.id = id;
        this.imageurl = imageurl;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageurl() {
        return imageurl;
    }

    public void setImageurl(Integer imageurl) {
        this.imageurl = imageurl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

