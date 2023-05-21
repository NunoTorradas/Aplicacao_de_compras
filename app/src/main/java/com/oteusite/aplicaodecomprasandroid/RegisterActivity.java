package com.oteusite.aplicaodecomprasandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class RegisterActivity extends AppCompatActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsernameEditText = findViewById(R.id.username_edittext);
        mPasswordEditText = findViewById(R.id.password_edittext);
        Button mRegisterButton = findViewById(R.id.register_button);

        mRegisterButton.setOnClickListener(v -> {
            String username = mUsernameEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            // TODO: Validate username and password

            // TODO: Register user
            DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
            boolean success = dbHelper.addUser(username, password);
            if (success) {
                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to next screen
                Login();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
            // TODO: Show success message and navigate to next screen
        });
    }
    public void Login(){
        Intent intent = new Intent(this, activity_login.class);
        startActivity(intent);
    }
}