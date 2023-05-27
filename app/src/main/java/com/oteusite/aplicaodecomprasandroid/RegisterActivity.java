package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextApelido;
    private EditText editTextMorada;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        editTextNome = findViewById(R.id.edit_text_nome);
        editTextApelido = findViewById(R.id.edit_text_apelido);
        editTextMorada = findViewById(R.id.edit_text_morada);
        editTextEmail = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonRegister = findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString().trim();
                String apelido = editTextApelido.getText().toString().trim();
                String morada = editTextMorada.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (nome.isEmpty() || apelido.isEmpty() || morada.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAdded = databaseHelper.addUser(nome, apelido, morada, email, password);
                    if (isAdded) {
                        Toast.makeText(RegisterActivity.this, "Utilizador registado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Falha ao registar o utilizador", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
