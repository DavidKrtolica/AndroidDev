package com.example.helloworld;

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

    public void aboutPressed(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void picsPressed(View view) {
        Intent intent = new Intent(this, PicsActivity.class);
        startActivity(intent);
    }

    public void similarPressed(View view) {
        Intent intent = new Intent(this, SimilarActivity.class);
        startActivity(intent);
    }
}