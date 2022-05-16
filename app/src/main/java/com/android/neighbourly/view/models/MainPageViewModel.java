package com.android.neighbourly.view.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.repositories.ProductRepository;

import java.util.List;

public class MainPageViewModel extends AndroidViewModel {

    private ProductRepository repository;

    public MainPageViewModel(@NonNull Application application) {
        super(application);
        repository = ProductRepository.getInstance();
    }

    public LiveData<List<Product>> getProducts(){
        return repository.getProducts();
    }
}
