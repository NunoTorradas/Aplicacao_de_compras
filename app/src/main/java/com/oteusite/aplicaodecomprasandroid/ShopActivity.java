package com.oteusite.aplicaodecomprasandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private ProductDatabaseHelper dbHelper;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        dbHelper = new ProductDatabaseHelper(this);

        // Verifica se os produtos já foram adicionados
        if (dbHelper.getProductsCount() == 0) {
            // Adiciona os produtos apenas se ainda não foram adicionados
            addProducts();
        }

        // Obtém a referência para o RecyclerView usando o ID correto
        RecyclerView recyclerView = findViewById(R.id.recycler_view_products);

        // Configurar o RecyclerView com um layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Obtém a lista de produtos da base de dados
        List<Product> productList = dbHelper.getAllProducts();

        // Cria um adapter com a lista de produtos
        adapter = new ProductAdapter(productList, this);

        // Define o adapter no RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private void addProducts() {
        Product product1 = new Product(1, "Produto 1", "10.99", "res/drawable/a1.jpg");
        Product product2 = new Product(2, "Produto 2", "19.99", "res/drawable/a2.jpg");
        Product product3 = new Product(3, "Produto 3", "5.99", "res/drawable/a3.jpg");

        dbHelper.addProduct(product1);
        dbHelper.addProduct(product2);
        dbHelper.addProduct(product3);
    }
}

