package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main_menu extends AppCompatActivity {

    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        textViewUsername = findViewById(R.id.text_view_menu_username);

        // Obter o nome do usuário da intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username != null) {
            textViewUsername.setText(username);
        }

        Button buttonModify = findViewById(R.id.button_modify);
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyActivity();
            }
        });

        Button buttonShop = findViewById(R.id.button_shop);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopActivity();
            }
        });

        Button buttonOrders = findViewById(R.id.button_orders);
        buttonOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrders();
            }
        });
    }

    private void openModifyActivity() {
        // TODO: Implement your code to open the activity for modifying username or password

        Intent intent = new Intent(this, ModifyActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Opening Modify Activity", Toast.LENGTH_SHORT).show();
    }

    private void openShopActivity() {
        // TODO: Implement your code to open the shop activity
        Toast.makeText(this, "Opening Shop Activity", Toast.LENGTH_SHORT).show();
    }

    private void showOrders() {
        // TODO: Implement your code to show the user's orders
        Toast.makeText(this, "Showing Orders", Toast.LENGTH_SHORT).show();
    }
}
