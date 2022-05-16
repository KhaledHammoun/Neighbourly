package com.android.neighbourly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.neighbourly.R;
import com.android.neighbourly.adapters.OrdersAdapter;
import com.android.neighbourly.view.models.HistoricalOrdersViewModel;

public class HistoricalOrdersFragment extends Fragment {

    private HistoricalOrdersViewModel viewModel;
    private RecyclerView ordersRecycler;
    private OrdersAdapter ordersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historical_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupView();
    }

    private void setupView() {
        ordersRecycler.hasFixedSize();
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.getAllOrders().observe(getViewLifecycleOwner(), orders -> {
            ordersAdapter.setOrders(orders);
        });

        ordersRecycler.setAdapter(ordersAdapter);
    }

    private void initView(View view) {
        viewModel = new ViewModelProvider(getActivity()).get(HistoricalOrdersViewModel.class);
        ordersRecycler = view.findViewById(R.id.historicalOrdersRecyclerView);
        ordersAdapter = new OrdersAdapter();
    }
}