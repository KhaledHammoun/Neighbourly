package com.android.neighbourly.view.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.model.classes.Order;
import com.android.neighbourly.model.classes.OrderLine;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.repositories.OrderRepository;
import com.android.neighbourly.repositories.ProductRepository;
import com.android.neighbourly.repositories.ShoppingCartRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

public class ShoppingCartViewModel extends AndroidViewModel {

    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private LiveData<List<CartItem>> allCartItems;
    private LiveData<List<Product>> allProducts;
    private Observer<List<CartItem>> observerOrderLines;
    private Observer<List<Product>> observerProducts;
    private List<Product> products;
    private List<OrderLine> orderLines;
    private Order order;

    public ShoppingCartViewModel(@NonNull Application application) {
        super(application);
        shoppingCartRepository = ShoppingCartRepository.getInstance(application);
        productRepository = ProductRepository.getInstance();
        orderRepository = OrderRepository.getInstance();
        allCartItems = shoppingCartRepository.getAllCartItems();
        allProducts = productRepository.getProducts();

        products = new ArrayList<>();
        orderLines = new ArrayList<>();
        order = new Order();
    }

    public LiveData<List<CartItem>> getCartItems(){
        return shoppingCartRepository.getAllCartItems();
    }

    private void createObservers() {
        observerOrderLines = cartItems -> {
            for (CartItem ci : cartItems) {
                String productId = ci.getProductId();
                Product product = products.stream().filter(p -> p.getId().equals(productId)).findFirst().orElse(null);
                if (product != null) {
                    OrderLine orderLine = new OrderLine(product.getPrice(), productId, ci.getQuantity());
                    orderLines.add(orderLine);
                }
            }

            order.setOrderLines(orderLines);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()).toString());

            orderRepository.addOrder(order);
            allCartItems.removeObserver(observerOrderLines);
        };

        observerProducts = products -> {
            this.products.addAll(products);
            allProducts.removeObserver(observerProducts);
        };
    }

    public LiveData<List<Product>> getProducts() {
        return productRepository.getProducts();
    }

    public void updateCartItem(CartItem item){
        shoppingCartRepository.updateCartItem(item);
    }

    public void deleteCartItem(CartItem item){
        shoppingCartRepository.deleteCartItem(item);
    }

    public String createOrder(){
        createObservers();

        allProducts.observeForever(observerProducts);
        allCartItems.observeForever(observerOrderLines);

        return "Test";
    }
}
