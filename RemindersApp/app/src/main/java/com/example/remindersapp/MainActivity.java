package com.example.remindersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.remindersapp.adapter.MyAdapter;
import com.example.remindersapp.model.Reminder;
import com.example.remindersapp.repo.MyRepo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Updateable {

    ArrayList<Reminder> reminders = new ArrayList<>();
    ListView listView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupListView();
        MyRepo.r().setUp(this, reminders);
    }

    private void setupListView() {
        listView = findViewById(R.id.remindersList);
        myAdapter = new MyAdapter(reminders, this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Clicked on row: " + position);
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("reminderId", reminders.get(position).getId());
            startActivity(intent);
        });
    }

    public void addPressed(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }

}