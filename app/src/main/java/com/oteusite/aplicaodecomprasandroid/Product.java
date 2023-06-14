package com.oteusite.aplicaodecomprasandroid;

public class Product {
    private int id;
    private String name;
    private String price;
    private String imageResource;

    public Product(int id, String name, String price, String imageResource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageResource() {
        return imageResource;
    }
}

