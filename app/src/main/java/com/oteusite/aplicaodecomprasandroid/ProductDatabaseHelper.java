package com.oteusite.aplicaodecomprasandroid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String COLUMN_CART_QUANTITY = "cartQuantity"; // Nova coluna para a quantidade no carrinho



    private Context context;

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PRICE + " TEXT," +
                COLUMN_IMAGE_PATH + " TEXT," +
                COLUMN_CREATED_AT + " INTEGER," +
                COLUMN_UPDATED_AT + " INTEGER," +
                COLUMN_CART_QUANTITY + " INTEGER DEFAULT 0" +
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
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH));
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

    public void updateProductImages() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPDATED_AT, new Date().getTime());
        db.update(TABLE_PRODUCTS, values, null, null);

        List<Product> productList = getAllProducts();
        for (Product product : productList) {
            new DownloadImageTask(product);
        }
        db.close();
    }

    private class DownloadImageTask extends AsyncTask<Void, Void, String> {
        private Product product;

        public DownloadImageTask(Product product) {
            this.product = product;
        }

        @Override
        protected String doInBackground(Void... params) {
            return downloadImageAndGetLocalPath(product.getImageResource());
        }

        @Override
        protected void onPostExecute(String localImagePath) {
            super.onPostExecute(localImagePath);
            if (localImagePath != null) {
                product.setImageResource(localImagePath);
                updateProductImagePath(product.getId(), localImagePath);
            }
        }
    }

    private String downloadImageAndGetLocalPath(String imageUrl) {
        String localImagePath = null;

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            File cacheDir = context.getCacheDir();
            File imageFile = new File(cacheDir, "product_image_" + System.currentTimeMillis() + ".jpg");

            FileOutputStream output = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();

            localImagePath = imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localImagePath;
    }

    private void updateProductImagePath(int productId, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_UPDATED_AT, new Date().getTime());
        db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }


}
