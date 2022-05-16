package com.android.neighbourly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.neighbourly.R;
import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.model.classes.Product;
import com.android.neighbourly.util.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<CartItem> shoppingCart;
    private List<Product> products;
    private OnClickListener<CartItem> increaseButtonListener;
    private OnClickListener<CartItem> decreaseButtonListener;
    private OnClickListener<CartItem> deleteButtonListener;

    public ShoppingCartAdapter(){
        this.shoppingCart = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public void setShoppingCartItems(List<CartItem> shoppingCartItems){
        shoppingCart = shoppingCartItems;
        notifyDataSetChanged();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_shopping_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.stream().filter(p -> p.getId().equals(shoppingCart.get(position).getProductId())).findFirst().orElse(null);
        if (product != null) {
            holder.title.setText(product.getName());
            holder.quantity.setText(String.valueOf(shoppingCart.get(position).getQuantity()));
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/products/" + product.getId() + ".jpg");
            GlideApp.with(holder.itemView).load(storageRef).into(holder.imageView);
            holder.price.setText(String.valueOf(product.getPrice()));
            holder.currency.setText("DKK");
            holder.totalPrice.setText(shoppingCart.get(position).getCartItemTotalPrice(product.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return shoppingCart.size();
    }

    public void setOnClickListenerIncrease(OnClickListener<CartItem> listener) {
        this.increaseButtonListener = listener;
    }

    public void setOnClickListenerDecrease(OnClickListener<CartItem> listener) {
        this.decreaseButtonListener = listener;
    }

    public void setOnClickListenerDelete(OnClickListener<CartItem> listener) {
        this.deleteButtonListener = listener;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView price;
        private TextView currency;
        private TextView totalPrice;
        private ImageButton increaseButton;
        private TextView quantity;
        private ImageButton decreaseButton;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id._imgProduct);
            title = itemView.findViewById(R.id._tvProductName);
            price = itemView.findViewById(R.id._tvPrice);
            currency = itemView.findViewById(R.id.shopping_cart_currency);
            totalPrice = itemView.findViewById(R.id._tvTotalPrice);
            increaseButton = itemView.findViewById(R.id._btnAddQty);
            quantity = itemView.findViewById(R.id._tvQuantity);
            decreaseButton = itemView.findViewById(R.id._btnMinusQty);
            deleteButton = itemView.findViewById(R.id._tvDelete);

            increaseButton.setOnClickListener(v -> {
                increaseButtonListener.onClick(shoppingCart.get(getBindingAdapterPosition()));
            });

            decreaseButton.setOnClickListener(v -> {
                decreaseButtonListener.onClick(shoppingCart.get(getBindingAdapterPosition()));
            });

            deleteButton.setOnClickListener(v -> {
                deleteButtonListener.onClick(shoppingCart.get(getBindingAdapterPosition()));
            });
        }
    }
}
