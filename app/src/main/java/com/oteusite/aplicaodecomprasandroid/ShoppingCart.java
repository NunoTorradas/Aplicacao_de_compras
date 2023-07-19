package com.oteusite.aplicaodecomprasandroid;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
    public void updateProductQuantity(Product product, int quantity) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                p.setQuantity(quantity);
                return;
            }
        }
    }

    public boolean containsProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }
}
