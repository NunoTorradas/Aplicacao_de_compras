package com.oteusite.aplicaodecomprasandroid;

public class Product {
    private int id;
    private String name;
    private String price;
    private String imagePath;

    public Product(int id, String name, String price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }
}
