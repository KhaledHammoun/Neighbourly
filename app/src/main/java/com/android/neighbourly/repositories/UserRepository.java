package com.android.neighbourly.repositories;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageButton;

import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.User;
import com.android.neighbourly.model.classes.livedata.ProfileLiveData;
import com.android.neighbourly.model.classes.livedata.UserLiveData;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserRepository {
    private final UserLiveData currentUser;
    private final DatabaseReference reference;
    private final Application app;
    private static UserRepository instance;

    private UserRepository(Application app) {
        this.app = app;
        currentUser = new UserLiveData();
        reference = FirebaseDatabase.getInstance("https://neighbourly-9d6f1-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    }

    public static synchronized UserRepository getInstance(Application app) {
        if(instance == null)
            instance = new UserRepository(app);
        return instance;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }


    public ProfileLiveData getUserProfile() {
        return new ProfileLiveData(reference, FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void editUserDetails(User user){
        reference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
        addUserImage(user.getImageUri());
    }

    private void addUserImage(Uri imageUri){
        StorageReference storageReference = FirebaseStorage.getInstance("gs://neighbourly-9d6f1.appspot.com").getReference().child("images/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
        storageReference.putFile(imageUri)
                .addOnSuccessListener(
                        taskSnapshot -> {
                            Log.i("Storage reference", "User image uploaded to storage reference");
                        })
                .addOnFailureListener(e -> {
                    Log.w("Storage reference", e.getMessage());
                });
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(app.getApplicationContext());
    }

}
