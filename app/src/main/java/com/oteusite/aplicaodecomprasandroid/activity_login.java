package com.oteusite.aplicaodecomprasandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_login extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);

        databaseHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                boolean loginSuccessful = checkLogin(username, password);
                if (loginSuccessful) {
                    Toast.makeText(activity_login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    openMainMenu(username);
                } else {
                    Toast.makeText(activity_login.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkLogin(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

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
            String storedPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
            return password.equals(storedPassword);
        }

        return false;
    }

    private void openMainMenu(String username) {
        Intent intent = new Intent(this, Main_menu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}

