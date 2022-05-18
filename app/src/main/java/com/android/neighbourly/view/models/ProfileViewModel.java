package com.android.neighbourly.view.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.neighbourly.model.classes.User;
import com.android.neighbourly.model.classes.livedata.ProfileLiveData;
import com.android.neighbourly.repositories.UserRepository;

public class ProfileViewModel extends AndroidViewModel {

    private UserRepository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public LiveData<User> getUserProfile(){
        return repository.getUserProfile();
    }

    public void editUserDetails(User user){
        repository.editUserDetails(user);
    }
}
