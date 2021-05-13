    package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
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

    static final int SELECT_PICTURE = 200;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgChosen = findViewById(R.id.imgChosen);
        nextBtn = findViewById(R.id.nextBtn);

        // SETTING UP THE NEXT BUTTON AND PASSING THE IMAGE TO THE NEXT ACTIVITY WHERE TEXT IS ADDED
        nextBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, EditSnapActivity.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            imgChosen.setDrawingCacheEnabled(true);
            Bitmap bitmap = imgChosen.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
            intent.putExtra("byteArray", bs.toByteArray());
            startActivity(intent);
            // CLEAR IMAGEVIEW AND BITMAP
            imgChosen.destroyDrawingCache();
            bitmap.recycle();
            intent.removeExtra("byteArray");
        });
    }

    // BUTTON FOR CHOOSING A PICTURE FROM THE GALLERY
    public void galleryPressed(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }

    // BUTTON FOR TAKING A PICTURE AND USING IT
    public void takePicPressed(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    // BUTTON FOR GOING TO THE CHAT MENU
    public void chatPressed(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        // FIRST PART OF IF-STATEMENT IS FOR THE CAMERA
        if (reqCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgChosen.setImageBitmap(imageBitmap);
            System.out.println("selected from camera");
        } else if (reqCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            // THIS PART IS IN CASE USER PICKS CHOOSE FROM GALLERY OPTION
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgChosen.setImageBitmap(selectedImage);
                System.out.println("selected from gallery");
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e);
            }
        }
    }

}