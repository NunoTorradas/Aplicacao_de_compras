package com.oteusite.aplicaodecomprasandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context) {
        this.context = context;
        this.orders = orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewOrderId;
        private TextView textViewOrderDate;
        private TextView textViewOrderTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewOrderTotal = itemView.findViewById(R.id.textViewOrderTotal);
        }

        public void bind(Order order) {
            textViewOrderId.setText("Order ID: " + order.getOrderId());
            textViewOrderDate.setText("Order Date: " + order.getOrderDate());
            textViewOrderTotal.setText("Order Total: $" + order.getOrderTotal());
        }
    }
}
