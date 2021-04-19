package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SimilarActivity extends AppCompatActivity {

    private TextView textViewSimilar;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar);
        textViewSimilar = findViewById(R.id.textViewSimilar);
        editText = findViewById(R.id.editText);
    }

    public void savePressed(View view) {
        textViewSimilar.setText(editText.getText());
        editText.setText("");
    }
}