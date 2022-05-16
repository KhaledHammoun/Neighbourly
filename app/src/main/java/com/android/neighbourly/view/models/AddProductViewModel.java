package com.android.neighbourly.view.models;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.repositories.ProductRepository;

public class AddProductViewModel extends AndroidViewModel {

    private ProductRepository repository;

    public AddProductViewModel(@NonNull Application application) {
        super(application);
        repository = ProductRepository.getInstance();
    }

    public void addProduct(Product product) {
        repository.addProduct(product);
    }
}
