package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextMorada;
    private Button buttonSave;

    private DatabaseHelper databaseHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        editTextNome = findViewById(R.id.edit_text_modify_nome);
        editTextEmail = findViewById(R.id.edit_text_modify_email);
        editTextMorada = findViewById(R.id.edit_text_modify_morada);
        buttonSave = findViewById(R.id.button_modify_save);

        databaseHelper = new DatabaseHelper(this);

        // Obter o nome de usuário do Intent
        username = getIntent().getStringExtra("username");

        // Obter os dados do usuário da base de dados
        User user = databaseHelper.getUser(username);
        if (user != null) {
            editTextNome.setText(user.getNome());
            editTextEmail.setText(user.getEmail());
            editTextMorada.setText(user.getMorada());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String morada = editTextMorada.getText().toString().trim();

                if (nome.isEmpty() || email.isEmpty() || morada.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean success = databaseHelper.updateUser(email, nome, morada);
                    if (success) {
                        Toast.makeText(ModifyActivity.this, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ModifyActivity.this, "Falha ao atualizar os dados", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
