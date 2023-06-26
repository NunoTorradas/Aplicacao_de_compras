package com.oteusite.aplicaodecomprasandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextMorada;
    private Button buttonSave;

    private String username;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        editTextUsername = findViewById(R.id.edit_text_modify_username);
        editTextPassword = findViewById(R.id.edit_text_modify_password);
        editTextNome = findViewById(R.id.edit_text_modify_nome);
        editTextEmail = findViewById(R.id.edit_text_modify_email);
        editTextMorada = findViewById(R.id.edit_text_modify_morada);
        buttonSave = findViewById(R.id.button_save);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username");
            loadUserData(username);
        }

        buttonSave.setOnClickListener(v -> saveUserData());
    }

    private void loadUserData(String username) {
        User user = databaseHelper.getUser(username);
        if (user != null) {
            String usernameText = user.getUsername();
            String passwordText = user.getPassword();
            String nomeText = user.getNome();
            String emailText = user.getEmail();
            String moradaText = user.getMorada();

            if (usernameText != null) {
                editTextUsername.setText(usernameText);
            }

            if (passwordText != null) {
                editTextPassword.setText(passwordText);
            }

            if (nomeText != null) {
                editTextNome.setText(nomeText);
            }

            if (emailText != null) {
                editTextEmail.setText(emailText);
            }

            if (moradaText != null) {
                editTextMorada.setText(moradaText);
            }
        }
    }


    private void saveUserData() {
        String password = editTextPassword.getText().toString().trim();
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String morada = editTextMorada.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please, insert a Password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nome)) {
            editTextNome.setError("Please, insert a Name");
            editTextNome.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please, insert a E-mail");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(morada)) {
            editTextMorada.setError("Please, insert a adress");
            editTextMorada.requestFocus();
            return;
        }

        boolean success = databaseHelper.updateUser(username, nome, email, morada, password);

        if (success) {
            Toast.makeText(this,"Data updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error updating data", Toast.LENGTH_SHORT).show();
        }
    }
}
