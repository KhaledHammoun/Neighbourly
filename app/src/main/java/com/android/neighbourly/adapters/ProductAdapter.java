package com.android.neighbourly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.neighbourly.R;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.util.GlideApp;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;
    private OnClickListener listener;

    public ProductAdapter() {
        products = new ArrayList<>();
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = products.get(position).getId();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/products/" + id + ".jpg");
        GlideApp.with(holder.itemView).load(storageRef).into(holder.image);
        holder.productTitle.setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnClickListener(OnClickListener<Product> listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView productTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_item_picture);
            productTitle = itemView.findViewById(R.id.product_item_title);

            itemView.setOnClickListener(v-> {
                listener.onClick(products.get(getBindingAdapterPosition()));
            });
        }
    }
}
