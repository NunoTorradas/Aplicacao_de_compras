package com.oteusite.aplicaodecomprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public  void signup(View view){
        startActivities(new Intent[]{new Intent(LoginActivity.this, MainActivity.class)});
    }
    public  void signin(View view){
        startActivities(new Intent[]{new Intent(LoginActivity.this, RegistrationActivity.class)});
    }
}