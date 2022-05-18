package com.android.neighbourly.model.classes;

import com.google.firebase.database.Exclude;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order {
    private String id;
    private List<OrderLine> orderLines;
    @Exclude
    private double totalPrice;
    private String orderDate;

    public Order() {
        orderLines = new ArrayList<>();
    }

    public Order(String id, List<OrderLine> orderLines, String orderDate) {
        this.id = id;
        this.orderLines = orderLines;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Exclude
    public double getTotalPrice() {
        return totalPrice;
    }

    @Exclude
    public void setTotalPrice() {
        for (OrderLine orderLine : orderLines) {
            totalPrice += orderLine.getTotalPrice();
        }
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(id, order.id) && Objects.equals(orderLines, order.orderLines) && Objects.equals(orderDate, order.orderDate);
    }
}

