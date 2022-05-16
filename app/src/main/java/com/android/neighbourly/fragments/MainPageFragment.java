package com.android.neighbourly.fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.neighbourly.R;
import com.android.neighbourly.adapters.ProductAdapter;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.view.models.MainPageViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainPageFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private NavController navController;
    private RecyclerView productsRecycler;
    private ProductAdapter adapter;
    private EditText searchBar;
    private MainPageViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initViews(View view) {
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        navController = Navigation.findNavController(view);
        productsRecycler = view.findViewById(R.id.products_recycleView);
        searchBar = view.findViewById(R.id.product_search_bar);
        viewModel = new ViewModelProvider(getActivity()).get(MainPageViewModel.class);
        adapter = new ProductAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupView();
    }

    private void setupView() {

        productsRecycler.hasFixedSize();
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            productsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            productsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
        });

        productsRecycler.setAdapter(adapter);

        adapter.setOnClickListener(product -> {
            Bundle bundle = new Bundle();
            bundle.putString("productId", product.getId());
            bundle.putString("providerId", product.getProviderId());
            navController.navigate(R.id.fragment_product_description, bundle);
        });

        //TODO edit
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
                    List<Product> collect = products.stream()
                            .filter(p -> p.getName().toLowerCase(Locale.ROOT).contains(editable.toString().toLowerCase(Locale.ROOT)))
                            .collect(Collectors.toList());
                    adapter.setProducts(collect);
                });

                productsRecycler.setAdapter(adapter);
            }
        });

        floatingActionButton.setOnClickListener(view -> {
            navController.navigate(R.id.addProductFragment);
        });
    }
}