package com.android.neighbourly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.neighbourly.R;
import com.android.neighbourly.adapters.ShoppingCartAdapter;
import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.view.models.ShoppingCartViewModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShoppingCartFragment extends Fragment {

    private ShoppingCartViewModel viewModel;
    private RecyclerView recyclerView;
    private ShoppingCartAdapter adapter;
    private NavController navController;
    private Button checkoutButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupView();
    }

    private void setupView() {
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ShoppingCartAdapter();

        viewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            adapter.setShoppingCartItems(cartItems);
        });

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
        });

        recyclerView.setAdapter(adapter);

        adapter.setOnClickListenerIncrease(item -> {
            if (item.getQuantity() >= 10)
            {
                Toast.makeText(getActivity(), "Maximum 10 pieces per product allowed!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                item.setQuantity(item.getQuantity() + 1);
                viewModel.updateCartItem(item);
            }
        });

        adapter.setOnClickListenerDecrease(item -> {
            if (item.getQuantity() <= 1)
            {
                Toast.makeText(getActivity(), "Only positive values allowed!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                item.setQuantity(item.getQuantity() - 1);
                viewModel.updateCartItem(item);
            }
        });

        adapter.setOnClickListenerDelete(item -> {
            viewModel.deleteCartItem(item);
        });

        checkoutButton.setOnClickListener(view -> {
            viewModel.createOrder();
            navController.navigate(R.id.checkoutFragment);
        });
    }

    private void initView(View view) {
        viewModel = new ViewModelProvider(getActivity()).get(ShoppingCartViewModel.class);
        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.shoppingCartRecyclerView);
        checkoutButton = view.findViewById(R.id.checkout_button);
    }
}