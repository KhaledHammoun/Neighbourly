package com.android.neighbourly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.neighbourly.R;
import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.util.GlideApp;
import com.android.neighbourly.view.models.ProductDescriptionViewModel;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductDescriptionFragment extends Fragment {

    private ProductDescriptionViewModel viewModel;
    private NavController navController;
    private TextView title;
    private ImageView image;
    private TextView category;
    private TextView provider;
    private TextView price;
    private TextView description;
    private NumberPicker quantity;
    private Button addToCart;
    private ImageButton favorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupView();
    }

    private void setupView() {
        quantity.setMinValue(1);
        //TODO check availability and add as max value
        quantity.setMaxValue(10);

        viewModel.getProduct(getArguments().getString("productId")).observe(getViewLifecycleOwner(), (product) -> {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/products/" + product.getId() + ".jpg");
            GlideApp.with(getActivity()).load(storageReference).into(image);

            title.setText(product.getName());
            provider.setText(product.getProviderId());
            price.setText(product.getPrice() + " DKK");
            description.setText(product.getDescription());
            category.setText(product.getCategory());

            addToCart.setOnClickListener(v -> {
                CartItem cartItem = new CartItem(quantity.getValue(), product.getId());
                viewModel.addProductToCart(cartItem);
                navController.navigate(R.id.shoppingCart);
            });
        });
    }

    private void initView(View view){
        viewModel = new ViewModelProvider(getActivity()).get(ProductDescriptionViewModel.class);
        title = view.findViewById(R.id.single_product_title);
        image = view.findViewById(R.id.single_product_image);
        category = view.findViewById(R.id.single_product_category);
        provider = view.findViewById(R.id.single_product_provider);
        price = view.findViewById(R.id.single_product_price);
        description = view.findViewById(R.id.single_product_description);
        quantity = view.findViewById(R.id.number_picker);
        addToCart = view.findViewById(R.id.add_to_cart_button);
        navController = Navigation.findNavController(view);
        favorite = view.findViewById(R.id.favoriteButton);
    }
}