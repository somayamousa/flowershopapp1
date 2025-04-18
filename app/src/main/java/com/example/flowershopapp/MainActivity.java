package com.example.flowershopapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirect to Intro or Home based on login status
        startActivity(new Intent(this, IntroductoryActivity.class));
        finish();
    }
}