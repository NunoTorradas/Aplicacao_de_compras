package com.oteusite.aplicaodecomprasandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String createProductsTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCTS + " (" +
                COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_TOTAL + " REAL)";
        db.execSQL(createProductsTableQuery);

        String createOrdersTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_ORDER_DATE + " TEXT)";
        db.execSQL(createOrdersTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implemente a lógica para atualizar o esquema do banco de dados, se necessário
        // Aqui você pode adicionar novas tabelas, modificar tabelas existentes, etc.
    }
}
