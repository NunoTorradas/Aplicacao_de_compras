package com.oteusite.aplicaodecomprasandroid;

public class Order {
    private int orderId;
    private String orderDate;
    private double orderTotal;

    public Order(int orderId, String orderDate, String orderTotal) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTotal = Double.parseDouble(orderTotal);
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
}
