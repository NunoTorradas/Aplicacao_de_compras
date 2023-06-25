package com.oteusite.aplicaodecomprasandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Main_menu extends AppCompatActivity {

    private TextView textViewUsername;
    private String username;
    private AlertDialog welcomeDialog;

    @SuppressLint("MissingInflatedId")
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
            showWelcomeDialog(username);
        }

        Button buttonModify = findViewById(R.id.button_modify);
        buttonModify.setOnClickListener(v -> openModifyActivity());

        Button buttonShop = findViewById(R.id.button_shop);
        buttonShop.setOnClickListener(v -> openShopActivity());

        Button buttonOrders = findViewById(R.id.button_orders);
        buttonOrders.setOnClickListener(v -> showOrders());
    }

    private void showWelcomeDialog(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_welcome, null);
        builder.setView(dialogView);
        TextView textViewWelcome = dialogView.findViewById(R.id.text_view_welcome);
        Button buttonOk = dialogView.findViewById(R.id.button_ok);

        String welcomeMessage = "Bem-vindo, " + username + "!";
        textViewWelcome.setText(welcomeMessage);

        welcomeDialog = builder.create();
        welcomeDialog.show();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeDialog.dismiss();
            }
        });
    }

    private void openModifyActivity() {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void openShopActivity() {
        Intent intent = new Intent(this, ShopActivity.class);

        Toast.makeText(this, "Opening Shop Activity", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void showOrders() {
        Intent intent = new Intent(this, OrdersActivity.class);
        Toast.makeText(this, "Showing Orders", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
