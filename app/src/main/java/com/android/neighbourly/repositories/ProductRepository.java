package com.android.neighbourly.repositories;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.model.classes.livedata.ProductLiveData;
import com.android.neighbourly.model.classes.livedata.SingleProductLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ProductRepository {
    public static ProductRepository instance;
    private DatabaseReference reference;
    private ProductLiveData productLiveData;

    private ProductRepository(){
        reference = FirebaseDatabase.getInstance("https://neighbourly-9d6f1-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        productLiveData = new ProductLiveData(reference);
    }

    public static synchronized ProductRepository getInstance(){
        if (instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    public ProductLiveData getProducts()
    {
        return productLiveData;
    }

    public SingleProductLiveData getSingleProductLiveData(String productId){
       return new SingleProductLiveData(reference, productId);
    }

    public void addProduct(Product product) {
        DatabaseReference reference = this.reference.child("products").push();
        String productId = reference.getKey();
        product.setProviderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.setValue(product);
        uploadProductImage(productId, product.getImageUri());
    }

    private void uploadProductImage(String productId, Uri imageUri) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/products/" + productId + ".jpg");
        storageRef.putFile(imageUri)
                .addOnSuccessListener(
                        taskSnapshot -> {
                        })
                .addOnFailureListener(e -> {
                    System.out.println(e.getMessage());
                });
    }
}
