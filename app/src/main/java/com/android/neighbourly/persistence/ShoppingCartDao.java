package com.android.neighbourly.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.model.classes.Product;

import java.util.List;

@Dao
public interface ShoppingCartDao {

    @Insert
    void addShoppingCart(CartItem shoppingCartItem);

    @Update
    void updateShoppingCartItem(CartItem shoppingCartItem);

    @Delete
    void deleteShoppingCart(CartItem shoppingCartItem);

    @Query("SELECT * FROM cart_item_line")
    LiveData<List<CartItem>> getAllCartItems();

    @Query("SELECT * FROM cart_item_line WHERE productId = :productId")
    CartItem findCartItemById(String productId);

    @Query("DELETE FROM cart_item_line")
    void emptyShoppingCart();
}
