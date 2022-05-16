package com.android.neighbourly.view.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.repositories.ProductRepository;
import com.android.neighbourly.repositories.ShoppingCartRepository;

public class ProductDescriptionViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private ShoppingCartRepository shoppingCartRepository;

    public ProductDescriptionViewModel(@NonNull Application application) {
        super(application);
        repository = ProductRepository.getInstance();
        shoppingCartRepository = ShoppingCartRepository.getInstance(application);
    }

    public LiveData<Product> getProduct(String productId){
        return repository.getSingleProductLiveData(productId);
    }

    public void addProductToCart(CartItem cartItem) {
        shoppingCartRepository.addCartItem(cartItem);
    }
}
