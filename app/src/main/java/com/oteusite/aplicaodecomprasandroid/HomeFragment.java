package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView catRecycleview;

    //Category recycleview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModels;

    public  HomeFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home_fragment, container, false);
        catRecycleview = root.findViewById(R.id.rec_category);

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
    }
}