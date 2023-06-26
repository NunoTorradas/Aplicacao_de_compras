package com.oteusite.aplicaodecomprasandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "purchase_database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_PRODUCTS = "products";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTAL = "total";

    public static final String TABLE_NAME_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_ORDER_DATE = "order_date";

    public PurchaseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");

        String createOrdersTableQuery = "CREATE TABLE " + TABLE_NAME_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_DATE + " TEXT)";

        String createProductsTableQuery = "CREATE TABLE " + TABLE_NAME_PRODUCTS + " (" +
                COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_ORDER_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_ORDER_ID + ") REFERENCES " + TABLE_NAME_ORDERS + "(" + COLUMN_ORDER_ID + "))";

        db.execSQL(createOrdersTableQuery);
        db.execSQL(createProductsTableQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implemente a lógica para atualizar o esquema do banco de dados, se necessário
        // Aqui você pode adicionar novas tabelas, modificar tabelas existentes, etc.
    }
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_ORDERS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_ID));
            @SuppressLint("Range") String orderDate = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_DATE));

            List<Product> products = getProductsForOrder(orderId);

            Order order = new Order(orderId, orderDate, products);
            orders.add(order);
        }

        cursor.close();
        return orders;
    }

    private List<Product> getProductsForOrder(int orderId) {
        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_PRODUCTS + " WHERE " + COLUMN_ORDER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(orderId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
            @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
            @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
            @SuppressLint("Range") int order_id = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_ID)); // Adicionado

            Product product = new Product(productId, productName, price, "", quantity);
            product.setOrderId(order_id);
            products.add(product);
        }

        cursor.close();
        return products;
    }
}
