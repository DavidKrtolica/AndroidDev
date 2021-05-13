package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.snapapp.repo.MyRepo;

public class DetailsActivity extends AppCompatActivity implements TaskListener {

    private ImageView snap;
    private Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        snap = findViewById(R.id.snap);

        String imgID = getIntent().getStringExtra("imgid");
        MyRepo.r().downloadBitmap(imgID, this);
    }

    @Override
    public void receive(byte[] bytes) {
        Bitmap databaseImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        snap.setImageBitmap(databaseImg);
    }

}