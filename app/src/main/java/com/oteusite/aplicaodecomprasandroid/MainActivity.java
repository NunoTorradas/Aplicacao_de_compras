package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oteusite.aplicaodecomprasandroid.adapter.CategoryAdapter;
import com.oteusite.aplicaodecomprasandroid.adapter.DiscountedProductAdapter;
import com.oteusite.aplicaodecomprasandroid.adapter.RecentlyViewedAdapter;
import com.oteusite.aplicaodecomprasandroid.model.Category;
import com.oteusite.aplicaodecomprasandroid.model.DiscountedProducts;
import com.oteusite.aplicaodecomprasandroid.model.RecentlyViewed;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecycleView, recentlyViewRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;
    List<RecentlyViewed> cart;
    TextView cartContentTextView;

    ImageView allCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecycleView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewRecycler = findViewById(R.id.recently_item);

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllCategory.class);
                startActivity(i);
            }
        });

        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, R.drawable.discountberry));
        discountedProductsList.add(new DiscountedProducts(2, R.drawable.discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, R.drawable.discountmeat));
        discountedProductsList.add(new DiscountedProducts(4, R.drawable.discountberry));
        discountedProductsList.add(new DiscountedProducts(5, R.drawable.discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(6, R.drawable.discountmeat));

        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_home_fruits, "Fruta"));
        categoryList.add(new Category(2, R.drawable.ic_home_fish, "Fruta1"));
        categoryList.add(new Category(3, R.drawable.ic_home_meats, "asd"));
        categoryList.add(new Category(4, R.drawable.ic_home_veggies, "Frueqwta"));
        categoryList.add(new Category(5, R.drawable.ic_home_fruits, "Frusadqweta"));
        categoryList.add(new Category(6, R.drawable.ic_home_fish, "Frutfdsfa"));
        categoryList.add(new Category(7, R.drawable.ic_home_meats, "Frqwewqeuta"));
        categoryList.add(new Category(8, R.drawable.ic_home_veggies, "Fru12ta"));

        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed("Fruta", "Fruta", "Fruta", "Fruta", "Fruta", R.drawable.card4, R.drawable.b4, "Fruta"));
        recentlyViewedList.add(new RecentlyViewed("Papaya", "Papaya", "4€", "1", "KG", R.drawable.card3, R.drawable.b3, "Fruta"));
        recentlyViewedList.add(new RecentlyViewed("Strawberry", "Strawberry", "3€", "1", "KG", R.drawable.card2, R.drawable.b2, "Fruta"));
        recentlyViewedList.add(new RecentlyViewed("Kiwi", "Kiwi", "1€", "1", "PX", R.drawable.card1, R.drawable.b1, "Fruta"));

        cart = new ArrayList<>();
        cartContentTextView = findViewById(R.id.cart_content_text_view); // Certifique-se de ter o TextView no layout activity_main.xml
        updateCartContent();

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewRecycler(recentlyViewedList);
    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this, dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }

    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecycleView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryDataList);
        categoryRecycleView.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                // Ao clicar em uma categoria, verificar se existem produtos com a mesma categoria
                List<String> productsWithSameCategory = new ArrayList<>();

                for (RecentlyViewed product : recentlyViewedList) {
                    if (product.getCategory().equalsIgnoreCase(category.getName())) {
                        productsWithSameCategory.add(product.getName());
                    }
                }

                if (!productsWithSameCategory.isEmpty()) {
                    // Se existirem produtos com a mesma categoria, mostrar a mensagem de Toast
                    String message = "Produtos com a categoria " + category.getName() + " encontrados: " + productsWithSameCategory.toString();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    // Caso contrário, mostrar uma mensagem informando que não foram encontrados produtos com a mesma categoria
                    String message = "Nenhum produto com a categoria " + category.getName() + " encontrado.";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRecentlyViewRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this, recentlyViewedDataList, new RecentlyViewedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecentlyViewed product) {
                cart.add(product);
                updateCartContent();
                Toast.makeText(MainActivity.this, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
            }
        });
        recentlyViewRecycler.setAdapter(recentlyViewedAdapter);
    }

    private void updateCartContent() {
        StringBuilder cartContent = new StringBuilder("Carrinho:\n");
        double total = 0.0;
        for (RecentlyViewed product : cart) {
            cartContent.append(product.getName()).append(" - R$ ").append(product.getPrice()).append("\n");
            total += Double.parseDouble(product.getPrice().replace("€", ""));
        }
        cartContent.append("Total: R$ ").append(String.format("%.2f", total));

        cartContentTextView.setText(cartContent.toString());
    }
}
