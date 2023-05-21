package com.oteusite.aplicaodecomprasandroid;
import android.content.Intent;
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

                if (checkCredentials(username, password)) {
                    openMainMenu(username);
                } else {
                    Toast.makeText(activity_login.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkCredentials(String username, String password) {
        // Verificar o nome de usuário e a senha na base de dados local
        return databaseHelper.authenticateUser(username, password);
    }

    private void openMainMenu(String username) {
        Intent intent = new Intent(this, Main_menu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}

