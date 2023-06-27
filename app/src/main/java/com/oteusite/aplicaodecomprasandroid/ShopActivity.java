package com.oteusite.aplicaodecomprasandroid;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends Activity {
    private ProductDatabaseHelper databaseHelper;
    private ListView productList;
    private ShoppingCart shoppingCart;

    private Button showCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        databaseHelper = new ProductDatabaseHelper(this);
        productList = findViewById(R.id.product_list);
        shoppingCart = new ShoppingCart();
        showCartButton = findViewById(R.id.btn_show_cart);
        showCartButton.setOnClickListener(v -> showCart());

        if (databaseHelper.getProductsCount() == 0) {
            addProducts();
            databaseHelper.updateProductImages();
        }

        List<Product> products = databaseHelper.getAllProducts();

        ProductListAdapter adapter = new ProductListAdapter(this, products);
        adapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                addToCart(product);
            }
        });


        productList.setAdapter(adapter);
    }

    private void addToCart(final Product product) {
        EditText quantityInput = findViewById(R.id.quantity_input);
        Button btnDecreaseQuantity = findViewById(R.id.btn_decrease_quantity);
        Button btnIncreaseQuantity = findViewById(R.id.btn_increase_quantity);

        // Obtém o valor inicial da quantidade do produto
        int initialQuantity = 0;
        if (!TextUtils.isEmpty(quantityInput.getText().toString())) {
            initialQuantity = Integer.parseInt(quantityInput.getText().toString());
        }

        // Define o valor inicial da quantidade no EditText
        quantityInput.setText(String.valueOf(initialQuantity));
        product.setQuantity(initialQuantity); // Define a quantidade inicial no objeto Product

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity(); // Obtém a quantidade atual do produto
                if (quantity > 0) {
                    quantity--;
                    quantityInput.setText(String.valueOf(quantity));
                    product.setQuantity(quantity);
                    addToCartLogic(product);
                }
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity(); // Obtém a quantidade atual do produto
                quantity++;
                quantityInput.setText(String.valueOf(quantity));
                product.setQuantity(quantity);
                addToCartLogic(product);
            }
        });

        // Configurar TextWatcher para monitorar alterações no campo de quantidade
        quantityInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não é necessário implementar este método
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String quantityString = s.toString();
                if (!TextUtils.isEmpty(quantityString)) {
                    int quantity = Integer.parseInt(quantityString);
                    product.setQuantity(quantity);
                    addToCartLogic(product);
                    Toast.makeText(ShopActivity.this, "Quantidade atualizada: " + quantity, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



    private void addToCartLogic(Product product) {
        boolean isProductInCart = false;
        for (Product cartProduct : shoppingCart.getProducts()) {
            if (cartProduct.getId() == product.getId()) {
                // Produto já está no carrinho, atualiza a quantidade
                int newQuantity = cartProduct.getQuantity() + product.getQuantity();
                cartProduct.setQuantity(newQuantity);
                isProductInCart = true;
                break;
            }
        }

        if (!isProductInCart) {
            // Produto não está no carrinho, adiciona-o
            Product newProduct = new Product(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
            shoppingCart.addProduct(newProduct);
        }

        // Exibe uma mensagem informando que a quantidade foi atualizada
        Toast.makeText(this, "Quantidade atualizada: " + product.getQuantity(), Toast.LENGTH_SHORT).show();
    }



    private void addProducts() {
        Product product1 = new Product(1, "Bread", "1.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dwb3524f22/images/col/737/7371247-frente.jpg?sw=2000&sh=2000", "Freshly baked bread");
        Product product2 = new Product(2, "Sugar", "0.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw52fd4a99/images/col/503/5038799-frente.jpg?sw=2000&sh=2000", "Granulated white sugar");
        Product product3 = new Product(3, "Salt", "3.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw9f22ef2c/images/col/562/5621031-hero.jpg?sw=2000&sh=2000", "sadasdwq");
        Product product4 = new Product(4, "Milk", "7.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw78b27074/images/col/406/4064882-frente.jpg?sw=2000&sh=2000","sadweqrewtdg");

        databaseHelper.addProduct(product1);
        databaseHelper.addProduct(product2);
        databaseHelper.addProduct(product3);
        databaseHelper.addProduct(product4);
    }

    private void showCart() {
        // Obtenha a lista de produtos no carrinho
        List<Product> cartProducts = shoppingCart.getProducts();

        // Crie um StringBuilder para exibir os produtos, quantidades e valores totais
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        for (Product product : cartProducts) {
            double productPrice = Double.parseDouble(product.getPrice());
            int quantity = product.getCartQuantity(); // Altere aqui para obter a quantidade do carrinho
            double totalProductPrice = productPrice * quantity;
            totalPrice += totalProductPrice;

            sb.append(product.getName()).append(" - Amount: ").append(quantity).append(" - Price: ").append(totalProductPrice).append("\n");
        }

        sb.append("Total Purchase Amount: ").append(totalPrice);

        // Exiba uma caixa de diálogo com os produtos no carrinho e o valor total da compra
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Products in Cart");
        builder.setMessage(sb.toString());
        builder.setPositiveButton("View Complete Cart", (dialog, which) -> {
            dialog.dismiss();
            // Obtenha a lista de produtos do carrinho
            List<Product> cartProducts1 = shoppingCart.getProducts();

            // Crie um bundle para passar os dados para a nova atividade
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("cartProducts", (ArrayList<? extends Parcelable>) cartProducts1);

            // Crie uma intenção para iniciar a atividade do carrinho
            Intent intent = new Intent(ShopActivity.this, openCartActivity.class);
            intent.putExtras(bundle);

            // Inicie a atividade do carrinho
            startActivity(intent);
        });
        builder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
