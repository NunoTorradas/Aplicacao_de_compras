package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToPage1(View view) {
        Intent intent = new Intent(this, Page1Activity.class);
        startActivity(intent);
    }
}