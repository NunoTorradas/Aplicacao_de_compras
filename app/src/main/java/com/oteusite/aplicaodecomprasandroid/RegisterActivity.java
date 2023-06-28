package com.oteusite.aplicaodecomprasandroid;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextMorada;
    private Button buttonRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextNome = findViewById(R.id.edit_text_nome);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextMorada = findViewById(R.id.edit_text_morada);
        buttonRegister = findViewById(R.id.button_register);

        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String morada = editTextMorada.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter a username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter a password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nome)) {
            editTextNome.setError("Please enter the name");
            editTextNome.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(morada)) {
            editTextMorada.setError("Please enter the address");
            editTextMorada.requestFocus();
            return;
        }

        boolean success = databaseHelper.insertUser(username, password, nome, email, morada);

        if (success) {
            Toast.makeText(this, "Successful registration", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error registering user", Toast.LENGTH_SHORT).show();
        }
    }
}
