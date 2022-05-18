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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.neighbourly.R;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.view.models.AddProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class AddProductFragment extends Fragment {

    private NavController navController;
    private ImageView imageView;
    private FloatingActionButton uploadButton;
    private AddProductViewModel viewModel;
    private Uri imageUri;
    private EditText productName;
    private EditText productDescription;
    private EditText productPrice;
    private Spinner productCategory;
    private Button saveProduct;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == -1){
                    imageUri = result.getData().getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = getContext().getContentResolver().loadThumbnail(imageUri, new Size(300, 300), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
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
        return inflater.inflate(R.layout.fragment_add_product, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupView();
    }

    private void setupView() {
        uploadButton.setOnClickListener((v) -> {
            SelectImage();
        });

        saveProduct.setOnClickListener(view1 -> {
            addProduct();
            Toast.makeText(getActivity(), "Product successfully added :)", Toast.LENGTH_SHORT).show();
        });
    }

    private void addProduct() {
        String nameText = productName.getText().toString();
        String descriptionText = productDescription.getText().toString();
        double price = Double.parseDouble(productPrice.getText().toString());
        String category = productCategory.getSelectedItem().toString();
        Product product = new Product(nameText, price, descriptionText, imageUri, category);

        viewModel.addProduct(product);
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        activityResultLauncher.launch(intent);
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        imageView = view.findViewById(R.id.imageView);
        uploadButton = view.findViewById(R.id.uploadImgButton);
        productName = view.findViewById(R.id.productNameTextField);
        productDescription = view.findViewById(R.id.productDescriptionTextField);
        productPrice = view.findViewById(R.id.productPriceNumberField);
        productCategory = view.findViewById(R.id.spinnerProductCategory);
        saveProduct = view.findViewById(R.id.saveProductButton);
        viewModel = new ViewModelProvider(this).get(AddProductViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);
    }
}