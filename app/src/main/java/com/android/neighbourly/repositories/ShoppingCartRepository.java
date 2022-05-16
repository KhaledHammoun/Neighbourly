package com.android.neighbourly.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.persistence.ShoppingCartDao;
import com.android.neighbourly.persistence.database.ShoppingCartDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingCartRepository {
    private static ShoppingCartRepository instance;
    private ShoppingCartDao dao;
    private ExecutorService executorService;
    private Handler mainThreadHandler;

    private ShoppingCartRepository(Application application) {
        ShoppingCartDatabase database = ShoppingCartDatabase.getInstance(application);
        dao = database.shoppingCartDao();
        executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    }

    public static synchronized ShoppingCartRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ShoppingCartRepository(application);
        }
        return instance;
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return dao.getAllCartItems();
    }

    public void addCartItem(CartItem item) {

        executorService.execute(() -> {
            CartItem result = dao.findCartItemById(item.getProductId());
            if (result != null){
                int quantity = result.getQuantity() + item.getQuantity();
                result.setQuantity(quantity);
                updateCartItem(result);
            }
            else {
                dao.addShoppingCart(item);
            }
            });


    }

    public void updateCartItem(CartItem item) {
        executorService.execute(() -> dao.updateShoppingCartItem(item));
    }

    public void deleteCartItem(CartItem item) {
        executorService.execute(() -> dao.deleteShoppingCart(item));
    }

    public void emptyShoppingCart(){
        executorService.execute(() -> dao.emptyShoppingCart());
    }
}
