package com.oteusite.aplicaodecomprasandroid;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> orders;
    private PurchaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recyclerView_orders);
        adapter = new OrderAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        dbHelper = new PurchaseDatabaseHelper(this);
        loadOrdersForUser();
    }

    private void loadOrdersForUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PurchaseDatabaseHelper.TABLE_NAME_ORDERS, null, null, null, null, null, null);

        orders = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex(PurchaseDatabaseHelper.COLUMN_ORDER_ID));
            @SuppressLint("Range") String orderDate = cursor.getString(cursor.getColumnIndex(PurchaseDatabaseHelper.COLUMN_ORDER_DATE));
            @SuppressLint("Range") String orderTotal = cursor.getString(cursor.getColumnIndex(PurchaseDatabaseHelper.COLUMN_TOTAL));

            Order order = new Order(orderId, orderDate, orderTotal);
            orders.add(order);
        }

        cursor.close();
        dbHelper.close();

        adapter.setOrders(orders);
    }

}
