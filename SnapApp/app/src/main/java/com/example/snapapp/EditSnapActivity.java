package com.example.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.snapapp.repo.MyRepo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class EditSnapActivity extends AppCompatActivity {

    private ImageView passingImg;
    private EditText editAddText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_snap);

        passingImg = findViewById(R.id.passingImg);
        editAddText = findViewById(R.id.editAddText);

        // HERE WE HAVE A SIMPLE METHOD WHICH TAKES IN THE INTENT EXTRA WHICH IS BEING SENT FROM
        // THE MAIN ACTIVITY CLASS UNDER THE NAME OF "byteArray" AND THEN USED FOR CREATING A BITMAP
        // AND THEN OBVIOUSLY TURNING IT INTO AN IMAGE AND SETTING IT INTO THE IMAGE VIEW
        if(getIntent().hasExtra("byteArray") && !getIntent().hasExtra("data")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            passingImg.setImageBitmap(b);
        }
    }

    // JON'S METHOD FOR DRAWING/PUTTING THE TEXT ONTO THE PICTURE,
    // HERE THE ONLY THING I CHANGED WAS THE COLOR OF THE TEXT TO BLACK
    // AND ALSO UPPED THE FONT TO A BIGGER SIZE FOR IT TO BE MORE READABLE
    public Bitmap drawTextToBitmap(Bitmap image, String gText) {
        Bitmap.Config bitmapConfig = image.getConfig();
        if(bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        image = image.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(image);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((int) (40)); // text size in pixels
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        canvas.drawText(gText, 150, 150, paint);
        return image;
    }

    // BUTTON FOR EXECUTING THE CODE WHICH DRAWS/PUTS TEXT ONTO THE PICTURE
    public void onAddTextPressed(View view) {
        passingImg.buildDrawingCache(true);
        Bitmap currentBitmap = ((BitmapDrawable)passingImg.getDrawable()).getBitmap();
        Bitmap drawnOn = drawTextToBitmap(currentBitmap, editAddText.getText().toString());
        passingImg.setImageBitmap(drawnOn);
    }

    // BUTTON WHICH SIMPLY SAVES THE IMAGE TO FIRESTORAGE, BUT ALSO CREATES AN
    // IMGREF DOCUMENT AND SAVES IT TO FIREBASE (THIS DOCUMENT CONTAINS ITS SELF-IF AND
    // NAME OF THE ACTUAL IMAGE)
    public void saveEditedImagePressed(View view) {
        passingImg.buildDrawingCache(true);
        Bitmap currentBitmap = ((BitmapDrawable)passingImg.getDrawable()).getBitmap();
        MyRepo.r().uploadBitmap(currentBitmap);
        MyRepo.r().addImgRef(currentBitmap.getGenerationId() + ".jpg");
        finish();
    }
}