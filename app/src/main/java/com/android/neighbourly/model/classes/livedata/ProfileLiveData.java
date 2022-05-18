package com.android.neighbourly.model.classes.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.model.classes.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileLiveData extends LiveData<User> {

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.getValue()!=null) {
                User user = snapshot.getValue(User.class);
                user.setId(snapshot.getKey());

                setValue(user);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private DatabaseReference reference;

    public ProfileLiveData(DatabaseReference reference, String uid) {
        this.reference = reference.child("users").child(uid).getRef();
    }

    @Override
    protected void onActive() {
        super.onActive();
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        reference.removeEventListener(listener);
    }
}
