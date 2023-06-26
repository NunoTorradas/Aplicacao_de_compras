package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class openCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> cartProducts;
    private PurchaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cart);

        // Obtenha os dados do carrinho completo da intenção
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cartProducts = bundle.getParcelableArrayList("cartProducts");
        }

        // Inicialize o RecyclerView e o adaptador
        recyclerView = findViewById(R.id.recyclerView_cart);
        adapter = new ProductAdapter(this, cartProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Configure o botão "Finalizar Compra"
        Button buttonFinishPurchase = findViewById(R.id.button_finish_purchase);
        buttonFinishPurchase.setOnClickListener(v -> registerPurchaseInDatabase());
    }

    private void registerPurchaseInDatabase() {
        dbHelper = new PurchaseDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Crie uma entrada na tabela de compras
        ContentValues purchaseValues = new ContentValues();
        purchaseValues.put(PurchaseDatabaseHelper.COLUMN_ORDER_DATE, getCurrentDate()); // Adicione a data da encomenda
        long purchaseId = db.insert(PurchaseDatabaseHelper.TABLE_NAME_ORDERS, null, purchaseValues);

        // Associe os produtos à compra
        for (Product product : cartProducts) {
            ContentValues productValues = new ContentValues();
            productValues.put(PurchaseDatabaseHelper.COLUMN_ORDER_ID, purchaseId); // Associe o produto ao ID da encomenda
            productValues.put(PurchaseDatabaseHelper.COLUMN_PRODUCT_NAME, product.getName());
            productValues.put(PurchaseDatabaseHelper.COLUMN_PRICE, Double.parseDouble(product.getPrice()));
            productValues.put(PurchaseDatabaseHelper.COLUMN_QUANTITY, product.getQuantity());
            double totalProductPrice = Double.parseDouble(product.getPrice()) * product.getQuantity();
            productValues.put(PurchaseDatabaseHelper.COLUMN_TOTAL, totalProductPrice);

            db.insert(PurchaseDatabaseHelper.TABLE_NAME_PRODUCTS, null, productValues);
        }

        String message;
        if (purchaseId != -1) {
            message = "Encomenda concluída com sucesso!";
        } else {
            message = "Falha ao concluir a encomenda.";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        dbHelper.close();
        startActivity(new Intent(this, Main_menu.class));

    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

}

