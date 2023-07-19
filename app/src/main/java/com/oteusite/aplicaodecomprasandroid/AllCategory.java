package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.oteusite.aplicaodecomprasandroid.adapter.AllCategoryAdapter;
import com.oteusite.aplicaodecomprasandroid.model.AllCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class AllCategory extends AppCompatActivity {

    RecyclerView AllCategoryRecycler;
    AllCategoryAdapter allCategoryAdapter;
    List<AllCategoryModel> allCategoryModelList;

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        AllCategoryRecycler = findViewById(R.id.all_category);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent back = new Intent(AllCategory.this, MainActivity.class);
                startActivity(back);
                finish();
            }

        });



        allCategoryModelList = new ArrayList<>();
        allCategoryModelList.add(new AllCategoryModel(1, R.drawable.ic_fruits));
        allCategoryModelList.add(new AllCategoryModel(2, R.drawable.ic_veggies));
        allCategoryModelList.add(new AllCategoryModel(3, R.drawable.ic_meat));
        allCategoryModelList.add(new AllCategoryModel(4, R.drawable.ic_fish));
        allCategoryModelList.add(new AllCategoryModel(5, R.drawable.ic_spices));
        allCategoryModelList.add(new AllCategoryModel(6, R.drawable.ic_egg));
        allCategoryModelList.add(new AllCategoryModel(7, R.drawable.ic_drink));
        allCategoryModelList.add(new AllCategoryModel(8, R.drawable.ic_cookies));
        allCategoryModelList.add(new AllCategoryModel(9, R.drawable.ic_juce));


        setCategoryRecycler(allCategoryModelList);
    }
    private void setCategoryRecycler(List<AllCategoryModel> allcategoryModelList) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        AllCategoryRecycler.setLayoutManager(layoutManager);
        allCategoryAdapter = new AllCategoryAdapter(this,allcategoryModelList);
        AllCategoryRecycler.setAdapter(allCategoryAdapter);
    }
}