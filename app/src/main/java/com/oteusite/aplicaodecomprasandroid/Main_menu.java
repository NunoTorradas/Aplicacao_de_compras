package com.oteusite.aplicaodecomprasandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main_menu extends AppCompatActivity {

    private TextView textViewUsername;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        textViewUsername = findViewById(R.id.text_view_menu_username);

        // Obter o nome de usuário do Intent
        Intent intent = getIntent();
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username");
            textViewUsername.setText(username);
        }

        Button buttonModify = findViewById(R.id.button_modify);
        buttonModify.setOnClickListener(v -> openModifyActivity());

        Button buttonShop = findViewById(R.id.button_shop);
        buttonShop.setOnClickListener(v -> openShopActivity());

        Button buttonOrders = findViewById(R.id.button_orders);
        buttonOrders.setOnClickListener(v -> showOrders());
    }

    private void openModifyActivity() {
        // Implemente o código para abrir a atividade de modificação de nome de usuário ou senha
        Toast.makeText(this, "Opening Modify Activity", Toast.LENGTH_SHORT).show();
    }

    private void openShopActivity() {
        // Implemente o código para abrir a atividade de compras
        Toast.makeText(this, "Opening Shop Activity", Toast.LENGTH_SHORT).show();
    }

    private void showOrders() {
        // Implemente o código para mostrar os pedidos do usuário
        Toast.makeText(this, "Showing Orders", Toast.LENGTH_SHORT).show();
    }
}

