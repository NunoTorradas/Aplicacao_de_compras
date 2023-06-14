package com.oteusite.aplicaodecomprasandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product_database";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_UPDATED_AT = "updated_at";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PRICE + " TEXT," +
                COLUMN_IMAGE_PATH + " TEXT," +
                COLUMN_CREATED_AT + " INTEGER," +
                COLUMN_UPDATED_AT + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        // Recreate the table
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE_PATH, product.getImageResource());
        long currentTime = new Date().getTime();
        values.put(COLUMN_CREATED_AT, currentTime);
        values.put(COLUMN_UPDATED_AT, currentTime);
        long id = db.insert(TABLE_PRODUCTS, null, values);
        product.setId((int) id); // Definir o ID gerado para o produto
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH));
                Product product = new Product(id, name, price, imagePath);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return productList;
    }

    public int getProductsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
