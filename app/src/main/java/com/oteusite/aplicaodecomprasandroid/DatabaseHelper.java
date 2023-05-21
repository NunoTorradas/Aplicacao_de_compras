package com.oteusite.aplicaodecomprasandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDatabase";

    // Tabela User
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NOME = "nome";
    private static final String COLUMN_USER_APELIDO = "apelido";
    private static final String COLUMN_USER_MORADA = "morada";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela User
        String createTableUser = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NOME + " TEXT, " +
                COLUMN_USER_APELIDO + " TEXT, " +
                COLUMN_USER_MORADA + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se houver uma versão anterior do banco de dados, você pode atualizá-la aqui
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean addUser(String nome, String apelido, String morada, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NOME, nome);
        values.put(COLUMN_USER_APELIDO, apelido);
        values.put(COLUMN_USER_MORADA, morada);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER_EMAIL};
        String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, projection, selection, selectionArgs, null, null, null);
        boolean authenticated = cursor.getCount() > 0;
        cursor.close();
        return authenticated;
    }
}



