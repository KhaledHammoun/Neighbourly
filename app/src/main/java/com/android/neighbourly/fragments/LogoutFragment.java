package com.android.neighbourly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.neighbourly.R;
import com.android.neighbourly.view.models.LogoutViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogoutFragment extends Fragment {

    private LogoutViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(LogoutViewModel.class);
        viewModel.logout();
    }
}