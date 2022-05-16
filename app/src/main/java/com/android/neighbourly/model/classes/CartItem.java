package com.android.neighbourly.model.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;
import java.util.Objects;

@Entity(tableName = "cart_item_line")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    private String productId;

    public CartItem(int quantity, String productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCartItemTotalPrice(double price){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(price*quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id && quantity == cartItem.quantity && Objects.equals(productId, cartItem.productId);
    }
}
