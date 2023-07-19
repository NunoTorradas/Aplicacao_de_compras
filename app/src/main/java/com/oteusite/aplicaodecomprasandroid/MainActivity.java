package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.oteusite.aplicaodecomprasandroid.adapter.CategoryAdapter;
import com.oteusite.aplicaodecomprasandroid.adapter.DiscountedProductAdapter;
import com.oteusite.aplicaodecomprasandroid.adapter.RecentlyViewedAdapter;
import com.oteusite.aplicaodecomprasandroid.model.Category;
import com.oteusite.aplicaodecomprasandroid.model.DiscountedProducts;
import com.oteusite.aplicaodecomprasandroid.R.drawable.*;
import com.oteusite.aplicaodecomprasandroid.model.RecentlyViewed;


public class MainActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecycleView, recentlyViewRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;
    ImageView allCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecycleView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewRecycler = findViewById(R.id.recently_item);


        allCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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
        categoryList.add(new Category(1, R.drawable.ic_home_fruits));
        categoryList.add(new Category(2, R.drawable.ic_home_fish));
        categoryList.add(new Category(3, R.drawable.ic_home_meats));
        categoryList.add(new Category(4, R.drawable.ic_home_veggies));
        categoryList.add(new Category(5, R.drawable.ic_home_fruits));
        categoryList.add(new Category(6, R.drawable.ic_home_fish));
        categoryList.add(new Category(7, R.drawable.ic_home_meats));
        categoryList.add(new Category(8, R.drawable.ic_home_veggies));

        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed("Watermelon", "watermelon","2€","1","KG", R.drawable.card4, R.drawable.b4 ));
        recentlyViewedList.add(new RecentlyViewed("Papaya", "Papaya","4€","1","KG", R.drawable.card3, R.drawable.b3));
        recentlyViewedList.add(new RecentlyViewed("Strawberry", "Strawberry","3€","1","KG", R.drawable.card2, R.drawable.b2 ));
        recentlyViewedList.add(new RecentlyViewed("Kiwi", "Kiwi","1€","1","PX", R.drawable.card1,R.drawable.b1 ));



        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewRecycler(recentlyViewedList);
    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }
    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecycleView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this,recentlyViewedDataList);
        recentlyViewRecycler.setAdapter(recentlyViewedAdapter);
    }
}