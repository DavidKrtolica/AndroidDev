    package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

    public class MainActivity extends AppCompatActivity {

    private ImageView imgChosen;
    private Button nextBtn;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgChosen = findViewById(R.id.imgChosen);
        nextBtn = findViewById(R.id.nextBtn);

        // SETTING UP THE NEXT BUTTON AND PASSING THE IMAGE
        nextBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, EditSnapActivity.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            imgChosen.setDrawingCacheEnabled(true);
            Bitmap bitmap = imgChosen.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
            intent.putExtra("byteArray", bs.toByteArray());
            startActivity(intent);
        });
    }

    public void galleryPressed(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgChosen.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println("Something went wrong");
        }
    }

}