package com.oteusite.aplicaodecomprasandroid;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private PurchaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recyclerView_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new PurchaseDatabaseHelper(this);
        List<Order> orders = dbHelper.getAllOrders();

        adapter = new OrderAdapter(this);
        adapter.setOrders(orders);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                recyclerView, (view, position) -> {
                    Order order = adapter.getItem(position);
                    showOrderDetails(order);
                }));
    }

    private void showOrderDetails(Order order) {
        List<Product> productList = dbHelper.getProductsByOrderId(order.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
        builder.setTitle("Order Details");

        LayoutInflater inflater = LayoutInflater.from(OrdersActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_order_details, null);
        builder.setView(dialogView);

        TextView textViewOrderId = dialogView.findViewById(R.id.textView_orderId);
        TextView textViewOrderDate = dialogView.findViewById(R.id.textView_orderDate);
        RecyclerView recyclerViewProducts = dialogView.findViewById(R.id.recyclerView_products);

        textViewOrderId.setText("Order ID: " + order.getId());
        textViewOrderDate.setText("Request Date:" + order.getDate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(OrdersActivity.this);
        recyclerViewProducts.setLayoutManager(layoutManager);

        if (productList != null) {
            ProductAdapter productAdapter = new ProductAdapter(OrdersActivity.this, productList);
            recyclerViewProducts.setAdapter(productAdapter);
        }

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
