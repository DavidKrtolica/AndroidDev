package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.snapapp.adapter.MyAdapter;
import com.example.snapapp.repo.MyRepo;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements Updateable {

    ArrayList<String> imageRefs = new ArrayList<>();
    ListView listView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupListView();

        MyRepo.r().setUp(this, imageRefs);
        System.out.println(imageRefs); // PRINTS EMPTY ARRAY, SO ISSUE -> REPO
    }

    private void setupListView() {
        listView = findViewById(R.id.chatList);
        myAdapter = new MyAdapter(imageRefs, this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Clicked on row: " + position);
            Intent intent = new Intent(ChatActivity.this, DetailsActivity.class);
            intent.putExtra("imgid", imageRefs.get(position));
            startActivity(intent);
        });
    }

    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }
}