package com.oteusite.aplicaodecomprasandroid;

public class CategoryModel {

    String img_utl;
    String name;
    String type;


    public CategoryModel(String img_utl, String name, String type) {
        this.img_utl = img_utl;
        this.name = name;
        this.type = type;
    }

    public String getImg_utl() {
        return img_utl;
    }

    public void setImg_utl(String img_utl) {
        this.img_utl = img_utl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
