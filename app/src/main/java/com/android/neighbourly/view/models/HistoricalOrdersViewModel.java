package com.android.neighbourly.view.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.neighbourly.fragments.HistoricalOrdersFragment;
import com.android.neighbourly.model.classes.Order;
import com.android.neighbourly.repositories.OrderRepository;

import java.util.List;

public class HistoricalOrdersViewModel extends ViewModel {

    private OrderRepository repository;

    public HistoricalOrdersViewModel(){
        repository = OrderRepository.getInstance();
    }

    public LiveData<List<Order>> getAllOrders(){
        return repository.getOrders();
    }
}
