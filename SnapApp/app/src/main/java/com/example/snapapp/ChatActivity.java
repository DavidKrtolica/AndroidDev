package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.snapapp.adapter.MyAdapter;
import com.example.snapapp.model.ImageRef;
import com.example.snapapp.repo.MyRepo;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements Updateable {

    ArrayList<ImageRef> imageRefs = new ArrayList<>();
    ListView listView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupListView();

        // SETTING UP THE REPO AND GETTING ALL IMAGE REFS ("imgRefs" COLLECTION)
        MyRepo.r().setUp(this, imageRefs);
    }

    // SETTING UP THE LIST VIEW AS THE CHAT/FEED FUNCTION OF THE APP TO SEE ALL IMAGES
    // HERE WE ARE PUTTING SOME EXTRAS IN THE INTENT SO WE CAN USE IT IN THE DETAILS
    // ACTIVITY FOR DELETING THE ACTUAL IMAGE FROM FIRESTORAGE AND THE DOCUMENT FROM
    // FIREBASE - ALSO USED FOR GETTING THE ACTUAL PICTURE THROUGH REFERENCE
    private void setupListView() {
        listView = findViewById(R.id.chatList);
        myAdapter = new MyAdapter(imageRefs, this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("Clicked on row: " + position);
            Intent intent = new Intent(ChatActivity.this, DetailsActivity.class);
            intent.putExtra("imgref", imageRefs.get(position).getRef());
            intent.putExtra("imgid", imageRefs.get(position).getId());
            startActivity(intent);
        });
    }

    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }
}