package com.android.neighbourly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.neighbourly.R;
import com.android.neighbourly.model.classes.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> orders;

    public OrdersAdapter(){
        orders = new ArrayList<>();
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_historical_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderId.setText(order.getId());
        holder.orderDate.setText(order.getOrderDate());
        holder.totalNumberOfItems.setText(String.valueOf(order.getOrderLines().size()));
        holder.orderTotalAmount.setText(String.valueOf(order.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView orderTotalAmount;
        private TextView orderId;
        private TextView totalNumberOfItems;
        private TextView orderDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTotalAmount = itemView.findViewById(R.id.orderTotalAmount);
            orderId = itemView.findViewById(R.id.orderId);
            totalNumberOfItems = itemView.findViewById(R.id.numberOfItems);
            orderDate = itemView.findViewById(R.id.orderDate);
        }
    }
}
