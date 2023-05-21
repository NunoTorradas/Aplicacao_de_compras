package com.oteusite.aplicaodecomprasandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        editTextNome = findViewById(R.id.edit_text_nome);
        editTextApelido = findViewById(R.id.edit_text_apelido);
        editTextMorada = findViewById(R.id.edit_text_morada);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonRegister = findViewById(R.id.button_register);

        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString();
                String apelido = editTextApelido.getText().toString();
                String morada = editTextMorada.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                boolean success = databaseHelper.addUser(nome, apelido, morada, email, password);
                if (success) {
                    Toast.makeText(RegisterActivity.this, "Usuário registrado com sucesso", Toast.LENGTH_SHORT).show();
                    finish(); // Fechar a atividade de registro
                } else {
                    Toast.makeText(RegisterActivity.this, "Falha ao registrar usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




