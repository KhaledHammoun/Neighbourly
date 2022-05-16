package com.android.neighbourly.model.classes.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SingleProductLiveData extends LiveData<Product> {

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Product product = snapshot.getValue(Product.class);
            product.setId(snapshot.getKey());

            setValue(product);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private DatabaseReference reference;

    public SingleProductLiveData(DatabaseReference reference, String productId) {
        this.reference = reference.child("products").child(productId).getRef();
    }

    @Override
    protected void onActive() {
        super.onActive();
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        reference.removeEventListener(listener);
    }
}
