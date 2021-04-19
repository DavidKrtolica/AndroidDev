package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView editTextView;
    Button saveBtn;
    EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editTextView = findViewById(R.id.editTextView);
        saveBtn = findViewById(R.id.buttonSave);
        editTextInput = findViewById(R.id.editTextInput);

        // GET THE TEXT FROM THE INTENT EXTRA DATA
        editTextView.setText(getIntent().getStringExtra("note"));
    }

    // TO-DO
    public void savePressed(View view) {

    }

}