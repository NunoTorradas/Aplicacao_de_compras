package com.oteusite.aplicaodecomprasandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "aplicacao_compras.db";
    private static final String TABLE_NAME = "users";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_NOME = "nome";
    public static final String COL_EMAIL = "email";
    public static final String COL_MORADA = "morada";
    public static final String COL_PASSWORD = "password";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + " TEXT," +
                COL_PASSWORD + " TEXT," +
                COL_NOME + " TEXT," +
                COL_EMAIL + " TEXT," +
                COL_MORADA + " TEXT)";
        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public boolean insertUser(String username, String password, String nome, String email, String morada) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_NOME, nome);
        values.put(COL_EMAIL, email);
        values.put(COL_MORADA, morada);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }

    public boolean updateUser(String username, String nome, String email, String morada, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NOME, nome);
        values.put(COL_EMAIL, email);
        values.put(COL_MORADA, morada);
        values.put(COL_PASSWORD, password);

        int rowsAffected = db.update(TABLE_NAME, values, COL_USERNAME + " = ?", new String[]{username});

        db.close(); // Fechar a conexão com o banco de dados

        return rowsAffected > 0;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_ID, COL_NOME, COL_EMAIL, COL_MORADA, COL_PASSWORD};
        String selection = COL_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COL_ID);
            int nomeIndex = cursor.getColumnIndex(COL_NOME);
            int emailIndex = cursor.getColumnIndex(COL_EMAIL);
            int moradaIndex = cursor.getColumnIndex(COL_MORADA);
            int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);

            if (idIndex != -1 && nomeIndex != -1 && emailIndex != -1 && moradaIndex != -1 && passwordIndex != -1) {
                int id = cursor.getInt(idIndex);
                String nome = cursor.getString(nomeIndex);
                String email = cursor.getString(emailIndex);
                String morada = cursor.getString(moradaIndex);
                String password = cursor.getString(passwordIndex);

                user = new User(id, username, nome, email, morada, password);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return user;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_USERNAME};
        String selection = COL_USERNAME + " = ?" + " AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

}
