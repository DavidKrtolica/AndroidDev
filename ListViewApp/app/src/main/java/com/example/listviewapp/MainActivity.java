package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listviewapp.adapter.MyAdapter;
import com.example.listviewapp.adapter.repo.Repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements Updateable {

    ArrayList<String> items = new ArrayList<>();
    ListView listView;
    MyAdapter myAdapter;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupAddBtn();
        setupListView();
        // READING DATA FROM FIREBASE AND ADDING IT TO THE ITEMS LIST
        Repo.r().setUp(this, items);
    }

    private void setupListView() {
        listView = findViewById(R.id.listView1);
        myAdapter = new MyAdapter(items, this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Clicked on row: " + position);
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("note", items.get(position));
            intent.putExtra("pos", position);
            startActivity(intent);
        });
    }

    private void setupAddBtn() {
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(e -> {
            Repo.r().addNote(); // ADD NEW NOTE TO FIREBASE
            items.add("new note " + items.size());
            // RELOADING DATA USING THE ADAPTER
            myAdapter.notifyDataSetChanged();
        });
    }

    // ANOTHER WAY OF DEALING WITH THE ADD BUTTON
    /*public void addNewRow(View view) {
        items.add("New note at " + items.size());
        // RELOADING DATA USING THE ADAPTER
        myAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }

}