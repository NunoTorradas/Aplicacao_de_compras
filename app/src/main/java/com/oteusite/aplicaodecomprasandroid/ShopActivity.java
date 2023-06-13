package com.oteusite.aplicaodecomprasandroid;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private ProductDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        dbHelper = new ProductDatabaseHelper(this);

        // Adiciona os produtos
        addProducts();

        // Obtém todos os produtos da base de dados
        List<Product> productList = dbHelper.getAllProducts();

        // Exibe os nomes dos produtos no Toast
        StringBuilder sb = new StringBuilder();
        for (Product product : productList) {
            sb.append(product.getName()).append("\n");
        }
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    private void addProducts() {
        Product product1 = new Product(1, "Produto 1", "10.99", "/res/drawable/a1.jpg");
        Product product2 = new Product(2, "Produto 2", "19.99", "/res/drawable/a2.jpg");
        Product product3 = new Product(3, "Produto 3", "5.99", "/res/drawable/a3.jpg");

        dbHelper.addProduct(product1);
        dbHelper.addProduct(product2);
        dbHelper.addProduct(product3);
    }
}

