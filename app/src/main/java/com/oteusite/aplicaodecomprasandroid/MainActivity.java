package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import com.oteusite.aplicaodecomprasandroid.adapter.CategoryAdapter;
import com.oteusite.aplicaodecomprasandroid.adapter.DiscountedProductAdapter;
import com.oteusite.aplicaodecomprasandroid.model.Category;
import com.oteusite.aplicaodecomprasandroid.model.DiscountedProducts;
import com.oteusite.aplicaodecomprasandroid.R.drawable.*;


public class MainActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecycleView;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecycleView = findViewById(R.id.categoryRecycler);


        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, R.drawable.discountberry));
        discountedProductsList.add(new DiscountedProducts(2, R.drawable.discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, R.drawable.discountmeat));
        discountedProductsList.add(new DiscountedProducts(4, R.drawable.discountberry));
        discountedProductsList.add(new DiscountedProducts(5, R.drawable.discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(6, R.drawable.discountmeat));

        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_home_fish));
        categoryList.add(new Category(2, R.drawable.ic_home_fish));
        categoryList.add(new Category(3, R.drawable.ic_home_fish));
        categoryList.add(new Category(4, R.drawable.ic_home_fish));
        categoryList.add(new Category(5, R.drawable.ic_home_fish));
        categoryList.add(new Category(1, R.drawable.ic_home_fish));
        categoryList.add(new Category(2, R.drawable.ic_home_fish));
        categoryList.add(new Category(3, R.drawable.ic_home_fish));
        categoryList.add(new Category(4, R.drawable.ic_home_fish));
        categoryList.add(new Category(5, R.drawable.ic_home_fish));


        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
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
}