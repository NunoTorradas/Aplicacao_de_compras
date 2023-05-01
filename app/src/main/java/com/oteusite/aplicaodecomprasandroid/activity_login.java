package com.oteusite.aplicaodecomprasandroid;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class activity_login extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                DatabaseHelper dbHelper = new DatabaseHelper(activity_login.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String[] projection = {
                        DatabaseHelper.COLUMN_USERNAME,
                        DatabaseHelper.COLUMN_PASSWORD
                };

                String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
                String[] selectionArgs = {username, password};

                Cursor cursor = db.query(
                        DatabaseHelper.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                if (cursor.moveToFirst()) {
                    Toast.makeText(activity_login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // TODO: Navigate to next screen
                } else {
                    Toast.makeText(activity_login.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}