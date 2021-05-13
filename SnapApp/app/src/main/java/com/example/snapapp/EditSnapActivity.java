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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class EditSnapActivity extends AppCompatActivity {

    private ImageView passingImg;
    private EditText editAddText;

    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_snap);

        passingImg = findViewById(R.id.passingImg);
        editAddText = findViewById(R.id.editAddText);

        // GETTING THE PIC FROM MAIN ACTIVITY
        if(getIntent().hasExtra("byteArray") && !getIntent().hasExtra("data")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            passingImg.setImageBitmap(b);
        }
    }

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

    public void onAddTextPressed(View view) {
        passingImg.buildDrawingCache(true);
        Bitmap currentBitmap = ((BitmapDrawable)passingImg.getDrawable()).getBitmap();
        Bitmap drawnOn = drawTextToBitmap(currentBitmap, editAddText.getText().toString());
        passingImg.setImageBitmap(drawnOn);
    }

    public void uploadBitmap(Bitmap bitmap) {
        int bitmapId = bitmap.getGenerationId();
        StorageReference ref = storage.getReference(bitmapId + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ref.putBytes(baos.toByteArray()).addOnCompleteListener(snap -> {
            System.out.println("OK to upload " + snap);
        }).addOnFailureListener(exception -> {
            System.out.println("failure to upload " + exception);
        });
    }

    public void saveEditedImagePressed(View view) {
        passingImg.buildDrawingCache(true);
        Bitmap currentBitmap = ((BitmapDrawable)passingImg.getDrawable()).getBitmap();
        uploadBitmap(currentBitmap);
        finish();
    }

}