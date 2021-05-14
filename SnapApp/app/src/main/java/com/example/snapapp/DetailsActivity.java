package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.example.snapapp.model.ImageRef;
import com.example.snapapp.repo.MyRepo;

public class DetailsActivity extends AppCompatActivity implements TaskListener {

    private ImageView snap;

    private String imgRef;
    private String imgID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        snap = findViewById(R.id.snap);

        // SIMPLY GETTING THE INTENT EXTRA SENT FROM CHAT ACTIVITY
        imgRef = getIntent().getStringExtra("imgref");
        imgID = getIntent().getStringExtra("imgid");

        MyRepo.r().downloadBitmap(imgRef, this);

        // VERY COOL METHOD I HAVE FOUND ON THE INTERNET, BASICALLY A SIMPLE TIMER
        // WHICH ALLOWS US TO EXECUTE SOME CODE AFTER A CERTAIN AMOUNT OF TIME PASSES,
        // IN THIS CASE 10 SECONDS UNTIL THE SNAP GETS DELETED
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("Snap ending soon!");
            }
            public void onFinish() {
                MyRepo.r().deleteRef(imgID);
                MyRepo.r().deleteDbImg(imgRef);
                finish();
            }
        }.start();
    }

    // THIS METHOD IS THE BUTTON-PRESS ACTION TO ALLOW THE USE TO
    // CLOSE SNAP RIGHT AWAY MANUALLY
    public void closePressed(View view) {
        MyRepo.r().deleteRef(imgID);
        MyRepo.r().deleteDbImg(imgRef);
        finish();
    }

    @Override
    public void receive(byte[] bytes) {
        Bitmap databaseImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        snap.setImageBitmap(databaseImg);
    }

}