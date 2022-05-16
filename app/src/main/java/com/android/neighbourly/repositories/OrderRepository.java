package com.android.neighbourly.repositories;

import com.android.neighbourly.model.classes.Order;
import com.android.neighbourly.model.classes.livedata.OrderLiveDate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderRepository {
    public static OrderRepository instance;
    private DatabaseReference reference;
    private OrderLiveDate orderLiveDate;

    private OrderRepository(){
        reference = FirebaseDatabase.getInstance("https://neighbourly-9d6f1-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        orderLiveDate = new OrderLiveDate(reference);
    }

    public static synchronized OrderRepository getInstance(){
        if (instance == null){
            instance = new OrderRepository();
        }
        return instance;
    }

    public OrderLiveDate getOrders(){
        return orderLiveDate;
    }

    public String addOrder(Order order){
        DatabaseReference orders = reference.child("orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
        String key = orders.getKey();
        order.setId(key);
        orders.setValue(order);

        return key;
    }
}
