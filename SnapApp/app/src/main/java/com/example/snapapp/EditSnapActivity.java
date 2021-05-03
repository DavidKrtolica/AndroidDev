package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;

public class EditSnapActivity extends AppCompatActivity {

    private ImageView passingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_snap);

        passingImg = findViewById(R.id.passingImg);

        // GETTING THE PIC FROM MAIN ACTIVITY
        if(getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            passingImg.setImageBitmap(b);
        }
    }
}