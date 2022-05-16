package com.android.neighbourly.model.classes.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.Order;
import com.android.neighbourly.model.classes.OrderLine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderLiveDate extends LiveData<List<Order>> {

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Order order = snapshot.getValue(Order.class);
            order.setId(snapshot.getKey());
            for (OrderLine ol :
                    order.getOrderLines().values()) {
                ol.setTotalPrice();
            }
            order.setTotalPrice();
            List<Order> orders = getValue();
            if (orders != null && !orders.contains(order)) {
                orders.add(order);
            }

            setValue(orders);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Order updated = snapshot.getValue(Order.class);
            updated.setId(snapshot.getKey());
            for (OrderLine ol :
                    updated.getOrderLines().values()) {
                ol.setTotalPrice();
            }
            updated.setTotalPrice();
            List<Order> orders = getValue();

            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getId().equals(snapshot.getKey())) {
                    orders.set(i, updated);
                    break;
                }
            }

            setValue(orders);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Order toDelete = snapshot.getValue(Order.class);
            List<Order> orders = getValue();
            assert orders != null;
            orders.remove(toDelete);
            setValue(orders);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private DatabaseReference reference;

    public OrderLiveDate(DatabaseReference reference) {
        this.reference = reference.child("orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getRef();
        setValue(new ArrayList<>());
    }

    @Override
    protected void onActive() {
        super.onActive();
        reference.addChildEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        reference.removeEventListener(listener);
    }
}
