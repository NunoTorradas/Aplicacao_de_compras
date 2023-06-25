package com.oteusite.aplicaodecomprasandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
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
        shoppingCart = new ShoppingCart(); // Inicialize o objeto ShoppingCart
        showCartButton = findViewById(R.id.btn_show_cart);
        showCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });
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
        ProductListAdapter adapter = new ProductListAdapter(this, products, new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                addToCart(product);
            }
        });

        // Definir o adaptador na ListView
        productList.setAdapter(adapter);
    }

    private void addProducts() {
        Product product1 = new Product(1, "Produto 1", "10.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw322cd103/images/col/769/7694878-frente.jpg?sw=2000&sh=2000");
        Product product2 = new Product(2, "Produto 2", "19.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw2065d2d7/images/col/205/2050172-frente.jpg?sw=2000&sh=2000");
        Product product3 = new Product(3, "Produto 3", "5.99", "https://img.freepik.com/fotos-gratis/respingo-colorido-abstrato-3d-background-generativo-ai-background_60438-2509.jpg");
        Product product4 = new Product(4, "Prodwewaeawe", "5.99", "https://img.freepik.com/fotos-gratis/respingo-colorido-abstrato-3d-background-generativo-ai-background_60438-2509.jpg");

        databaseHelper.addProduct(product1);
        databaseHelper.addProduct(product2);
        databaseHelper.addProduct(product3);
        databaseHelper.addProduct(product4);
    }

    public void addToCart(final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quantidade");
        builder.setMessage("Insira a quantidade desejada:");

        final EditText quantityInput = new EditText(this);
        quantityInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(quantityInput);

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantityString = quantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantityString)) {
                    int quantity = Integer.parseInt(quantityString);
                    if (quantity > 0) {
                        product.setQuantity(quantity);
                        addToCartLogic(product);
                    } else {
                        Toast.makeText(ShopActivity.this, "Selecione uma quantidade válida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShopActivity.this, "Insira uma quantidade válida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addToCartLogic(Product product) {
        if (shoppingCart.containsProduct(product)) {
            // Atualizar a quantidade do produto existente no carrinho
            shoppingCart.updateProductQuantity(product, product.getQuantity());
        } else {
            // Adicionar o novo produto ao carrinho
            shoppingCart.addProduct(product);
        }

        Toast.makeText(this, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
    }



    private void showCart() {
        // Obtenha a lista de produtos no carrinho
        List<Product> cartProducts = shoppingCart.getProducts();

        // Crie um StringBuilder para exibir os produtos, quantidades e valores totais
        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;
        for (Product product : cartProducts) {
            double productPrice = Double.parseDouble(product.getPrice());
            int quantity = product.getQuantity();
            double totalProductPrice = productPrice * quantity;
            totalPrice += totalProductPrice;

            sb.append(product.getName()).append(" - Quantidade: ").append(quantity).append(" - Valor Total: ").append(totalProductPrice).append("\n");
        }

        sb.append("Valor Total da Compra: ").append(totalPrice);

        // Exiba uma caixa de diálogo com os produtos no carrinho e o valor total da compra
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Produtos no Carrinho");
        builder.setMessage(sb.toString());
        builder.setPositiveButton("Ver Carrinho Completo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Obtenha a lista de produtos do carrinho
                List<Product> cartProducts = shoppingCart.getProducts();

                // Crie um bundle para passar os dados para a nova atividade
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("cartProducts", (ArrayList<? extends Parcelable>) new ArrayList<Product>(cartProducts));

                // Crie uma intenção para abrir a atividade do carrinho completo
                Intent intent = new Intent(ShopActivity.this, openCartActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
        builder.setNegativeButton("OK", null);
        builder.show();
    }





}
