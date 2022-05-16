package com.android.neighbourly.model.classes;

import com.google.firebase.database.Exclude;

import java.util.Objects;

public class OrderLine {
    private String id;
    private double price;
    @Exclude
    private double totalPrice;
    private String productId;
    private int quantity;

    public OrderLine() {
    }

    public OrderLine(double price, String productId, int quantity) {
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Exclude
    public double getTotalPrice() {
        return totalPrice;
    }

    @Exclude
    public void setTotalPrice() {
       totalPrice = quantity * price;
    }

    public String getProduct() {
        return productId;
    }

    public void setProduct(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return Double.compare(orderLine.price, price) == 0 && Double.compare(orderLine.totalPrice, totalPrice) == 0 && quantity == orderLine.quantity && Objects.equals(id, orderLine.id) && Objects.equals(productId, orderLine.productId);
    }
}
