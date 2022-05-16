package com.android.neighbourly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.neighbourly.R;
import com.android.neighbourly.view.models.CheckoutViewModel;
import com.bumptech.glide.Glide;


public class CheckoutFragment extends Fragment {

    private NavController navController;
    private CheckoutViewModel viewModel;
    private ImageView imageView;
    private TextView textView;
    private Button goBackButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupView();
    }

    private void setupView() {
        textView.setText("Thank you for your order! Your order number is ");

        goBackButton.setOnClickListener(view -> {
            navController.navigate(R.id.navMain);
        });

    }

    private void initView(View view) {
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(CheckoutViewModel.class);
        imageView = view.findViewById(R.id.gifImageVierw);
        textView = view.findViewById(R.id.orderNumberTextView);
        goBackButton = view.findViewById(R.id.backToMainMenuButton);

        Glide.with(view).load("https://c.tenor.com/VgCDirag6VcAAAAi/party-popper-joypixels.gif").into(imageView);
    }
}