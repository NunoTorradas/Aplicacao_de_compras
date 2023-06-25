package com.oteusite.aplicaodecomprasandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class ShopActivity extends Activity {
    private ProductDatabaseHelper databaseHelper;
    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        databaseHelper = new ProductDatabaseHelper(this);
        productList = findViewById(R.id.product_list);

        // Verifica se os produtos já foram adicionados
        if (databaseHelper.getProductsCount() == 0) {
            // Adiciona os produtos apenas se ainda não foram adicionados
            addProducts();
            // Atualiza as imagens dos produtos
            databaseHelper.updateProductImages();
        }

        // Obter a lista de produtos do banco de dados
        List<Product> products = databaseHelper.getAllProducts();

        // Criar um adaptador personalizado para exibir os produtos na ListView
        ProductListAdapter adapter = new ProductListAdapter(this, products);

        // Definir o adaptador na ListView
        productList.setAdapter(adapter);
    }

    private void addProducts() {
        Product product1 = new Product(1, "Produto 1", "10.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw322cd103/images/col/769/7694878-frente.jpg?sw=2000&sh=2000");
        Product product2 = new Product(2, "Produto 2", "19.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw2065d2d7/images/col/205/2050172-frente.jpg?sw=2000&sh=2000");
        Product product3 = new Product(3, "Produto 3", "5.99", "https://img.freepik.com/fotos-gratis/respingo-colorido-abstrato-3d-background-generativo-ai-background_60438-2509.jpg");

        databaseHelper.addProduct(product1);
        databaseHelper.addProduct(product2);
        databaseHelper.addProduct(product3);
    }
}
