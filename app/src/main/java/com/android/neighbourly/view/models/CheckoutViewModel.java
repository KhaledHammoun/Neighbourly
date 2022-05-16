package com.android.neighbourly.view.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.Order;
import com.android.neighbourly.repositories.OrderRepository;
import com.android.neighbourly.repositories.ProductRepository;
import com.android.neighbourly.repositories.ShoppingCartRepository;

import java.util.List;

public class CheckoutViewModel extends AndroidViewModel {

    private ShoppingCartRepository shoppingCartRepository;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        shoppingCartRepository = ShoppingCartRepository.getInstance(application);
        orderRepository = OrderRepository.getInstance();
        productRepository = ProductRepository.getInstance();
        shoppingCartRepository.emptyShoppingCart();
    }

    public LiveData<List<Order>> getAllOrders(){
        return orderRepository.getOrders();
    }


}
