package com.oteusite.aplicaodecomprasandroid;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String morada = editTextMorada.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Por favor, insira um nome de usuário");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Por favor, insira uma senha");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nome)) {
            editTextNome.setError("Por favor, insira o nome");
            editTextNome.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Por favor, insira o e-mail");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(morada)) {
            editTextMorada.setError("Por favor, insira a morada");
            editTextMorada.requestFocus();
            return;
        }

        boolean success = databaseHelper.insertUser(username, password, nome, email, morada);

        if (success) {
            Toast.makeText(this, "Registro bem-sucedido", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao registrar o usuário", Toast.LENGTH_SHORT).show();
        }
    }
}
