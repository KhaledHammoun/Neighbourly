package com.android.neighbourly.view.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.neighbourly.repositories.UserRepository;

public class LogoutViewModel extends AndroidViewModel {
    private UserRepository repository;

    public LogoutViewModel(@NonNull Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public void logout() {
        repository.signOut();
    }
}
