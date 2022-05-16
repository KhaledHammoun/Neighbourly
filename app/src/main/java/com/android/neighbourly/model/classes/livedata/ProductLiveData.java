package com.android.neighbourly.model.classes.livedata;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ProductLiveData extends LiveData<List<Product>> {

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Product product = snapshot.getValue(Product.class);
            product.setId(snapshot.getKey());
            List<Product> products = getValue();
            if (products != null && !products.contains(product)) {
                products.add(product);
            }

            setValue(products);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Product updated = snapshot.getValue(Product.class);
            updated.setId(snapshot.getKey());
            List<Product> products = getValue();

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(snapshot.getKey())) {
                    products.set(i, updated);
                    break;
                }
            }

            setValue(products);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Product toDelete = snapshot.getValue(Product.class);
            toDelete.setId(snapshot.getKey());
            List<Product> products = getValue();
            assert products != null;
            products.remove(toDelete);
            setValue(products);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private DatabaseReference databaseReference;

    public ProductLiveData(DatabaseReference ref) {
        databaseReference = ref.child("products");
        setValue(new ArrayList<>());
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addChildEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }
}
