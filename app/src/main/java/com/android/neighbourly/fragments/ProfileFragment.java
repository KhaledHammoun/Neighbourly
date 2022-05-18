package com.android.neighbourly.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.neighbourly.R;
import com.android.neighbourly.model.classes.User;
import com.android.neighbourly.util.GlideApp;
import com.android.neighbourly.view.models.ProfileViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class ProfileFragment extends Fragment {

    private FloatingActionButton uploadImageButton;
    private Button updateProfileButton;
    private EditText name;
    private EditText mobile;
    private EditText email;
    private Uri imageUri;
    private ImageView imageView;
    private ProfileViewModel viewModel;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == -1){
                    imageView.refreshDrawableState();
                    imageUri = result.getData().getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = getContext().getContentResolver().loadThumbnail(imageUri, new Size(300, 300), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GlideApp.with(this)
                            .load(bitmap)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imageView);
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupView();
    }

    private void initView(View view) {
       uploadImageButton = view.findViewById(R.id.profile_upload_image);
       updateProfileButton = view.findViewById(R.id.profile_save);
       name = view.findViewById(R.id.profile_name);
       mobile = view.findViewById(R.id.profile_number);
       email = view.findViewById(R.id.profile_email);
       imageView = view.findViewById(R.id.profile_image);
       viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
    }

    private void setupView() {
        viewModel.getUserProfile().observe(getViewLifecycleOwner(), observedUser ->{
            name.setText(observedUser.getName());
            mobile.setText(observedUser.getMobile());
            email.setText(observedUser.getEmail());

            StorageReference reference = FirebaseStorage.getInstance("gs://neighbourly-9d6f1.appspot.com")
                    .getReference().child("images/users/" + observedUser.getId() + ".jpg");

            GlideApp
                    .with(this)
                    .load(reference)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        });

        uploadImageButton.setOnClickListener((view -> {
            SelectImage();
        }));

        updateProfileButton.setOnClickListener(view -> {
            updateProfile();
        });
    }

    private void updateProfile() {
        String nameToEdit = name.getText().toString();
        String mobileToEdit = mobile.getText().toString();
        String emailToEdit = email.getText().toString();
        User user = new User(nameToEdit, mobileToEdit, emailToEdit, imageUri);

        viewModel.editUserDetails(user);
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        activityResultLauncher.launch(intent);
    }
}