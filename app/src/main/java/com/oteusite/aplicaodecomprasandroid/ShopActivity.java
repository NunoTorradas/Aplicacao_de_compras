package com.oteusite.aplicaodecomprasandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
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
        ProductListAdapter adapter = new ProductListAdapter(products);
        productList.setAdapter(adapter);

        Spinner categorySpinner = findViewById(R.id.category_spinner);
        List<Category> categories = createCategories(); // Obtenha a lista de categorias existentes
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

    }

    private void addProducts() {
        // Adicione seus produtos aqui
        // Exemplo:
        Product product1 = new Product(1, "Bread", "1.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dwb3524f22/images/col/737/7371247-frente.jpg?sw=2000&sh=2000", "Food");
        Product product2 = new Product(2, "Sugar", "0.99", "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw52fd4a99/images/col/503/5038799-frente.jpg?sw=2000&sh=2000", "Food");
        // ...

        databaseHelper.addProduct(product1);
        databaseHelper.addProduct(product2);
        // ...
    }

    private List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();
        // Adicione as categorias aqui
        categories.add(new Category("Food"));
        categories.add(new Category("Electronics"));
        categories.add(new Category("Clothing"));
        // ...
        return categories;
    }

    public void addToCart(final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Amount");
        builder.setMessage("Enter the desired quantity:");

        final EditText quantityInput = new EditText(this);
        quantityInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(quantityInput);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String quantityString = quantityInput.getText().toString();
            if (!TextUtils.isEmpty(quantityString)) {
                int quantity = Integer.parseInt(quantityString);
                if (quantity > 0) {
                    product.setQuantity(quantity);
                    addToCartLogic(product);
                } else {
                    Toast.makeText(ShopActivity.this, "Select a valid quantity", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ShopActivity.this, "Enter a valid quantity", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);

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

        Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
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
            bundle.putParcelableArrayList("cartProducts", new ArrayList<>(cartProducts1));

            // Crie uma intenção para abrir a atividade do carrinho completo
            Intent intent = new Intent(ShopActivity.this, openCartActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        builder.setNegativeButton("OK", null);
        builder.show();
    }

    class ProductListAdapter extends BaseAdapter {
        private List<Product> products;

        public ProductListAdapter(List<Product> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            }

            TextView txtProductName = convertView.findViewById(R.id.txt_product_name);
            TextView productPrice = convertView.findViewById(R.id.product_price);

            final Product product = products.get(position);

            txtProductName.setText(product.getName());
            productPrice.setText(product.getPrice());

            convertView.setOnClickListener(v -> addToCart(product));

            return convertView;
        }
    }

    class CategoryPagerAdapter extends PagerAdapter {
        private List<Category> categories;

        public CategoryPagerAdapter(List<Category> categories) {
            this.categories = categories;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_category, container, false);

            TextView categoryName = view.findViewById(R.id.category_name);
            categoryName.setText(categories.get(position).getName());

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).getName();
        }
    }
}
