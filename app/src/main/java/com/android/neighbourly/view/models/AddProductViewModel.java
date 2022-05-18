package com.android.neighbourly.view.models;

import androidx.lifecycle.ViewModel;

import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.repositories.ProductRepository;

public class AddProductViewModel extends ViewModel {

    private ProductRepository repository;

    public AddProductViewModel() {
        repository = ProductRepository.getInstance();
    }

    public void addProduct(Product product) {
        repository.addProduct(product);
    }
}
