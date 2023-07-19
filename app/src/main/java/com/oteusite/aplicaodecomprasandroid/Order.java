package com.oteusite.aplicaodecomprasandroid;

import java.util.List;

public class Order {
    private int id;
    private String date;
    private List<Product> products;

    public Order(int id, String date, List<Product> products) {
        this.id = id;
        this.date = date;
        this.products = products;

    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
