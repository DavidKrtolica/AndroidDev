package com.example.remindersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.remindersapp.repo.MyRepo;

public class AddActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDesc;
    private EditText editDate;
    private EditText editTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);
    }

    public void savePressed(View view){
        MyRepo.r().addReminder(editTitle.getText().toString(), editDesc.getText().toString(),
                               editDate.getText().toString(), editTime.getText().toString());
        finish();
    }

}