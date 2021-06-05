package com.example.remindersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.remindersapp.model.Reminder;
import com.example.remindersapp.repo.MyRepo;

public class DetailsActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDesc;
    private EditText editDate;
    private EditText editTime;

    private Reminder currentReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);

        String reminderId = getIntent().getStringExtra("reminderId");
        currentReminder = MyRepo.r().getReminderWith(reminderId);

        editTitle.setText(currentReminder.getTitle());
        editDesc.setText(currentReminder.getDescription());
        editDate.setText(currentReminder.getDate());
        editTime.setText(currentReminder.getTime());

    }

    public void updatePressed(View view) {
        MyRepo.r().updateReminder(new Reminder(currentReminder.getId(), editTitle.getText().toString(), editDesc.getText().toString(),
                                               editDate.getText().toString(), editTime.getText().toString()));
        finish();
    }

    public void deletePressed(View view){
        MyRepo.r().deleteReminder(currentReminder.getId());
        finish();
    }

}