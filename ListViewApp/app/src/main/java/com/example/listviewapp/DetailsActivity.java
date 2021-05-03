package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listviewapp.adapter.repo.Repo;
import com.example.listviewapp.model.Note;
import com.google.firebase.storage.StorageReference;

public class DetailsActivity extends AppCompatActivity implements TaskListener {

    private TextView editTextView;
    private EditText editTextInput;
    private ImageView imageView;
    private Bitmap currentBitmap;
    private Note currentNote;
    private ImageView dbImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editTextView = findViewById(R.id.editTextView);
        editTextInput = findViewById(R.id.editTextInput);
        imageView = findViewById(R.id.imageView);
        dbImg = findViewById(R.id.dbImg);

        // GET THE TEXT FROM THE INTENT EXTRA DATA
        String noteID = getIntent().getStringExtra("noteid");
        currentNote = Repo.r().getNoteWith(noteID);
        editTextView.setText(currentNote.getText());

        // DOWNLOADING THE IMAGE BITMAPS FROM FIREBASE STORAGE
        Repo.r().downloadBitmap(noteID, this);
    }

    // UPLOAD IMAGE TO FIREBASE STORAGE BY PRESSING SAVE
    public void setupStaticImage(View view) {
        // MUST BE CALLED AFTER "onCreate()" IS FINISHED
        imageView.buildDrawingCache(true);
        currentBitmap = Bitmap.createBitmap(imageView.getDrawingCache(true));
        currentNote.setText(editTextView.getText().toString());
        Repo.r().uploadBitmap(currentNote, currentBitmap);
        finish();
    }

    // UPDATE NOTE
    public void savePressed(View view) {
        Repo.r().updateNote(new Note(currentNote.getId(), editTextInput.getText().toString()));
        finish();
    }

    // DELETE NOTE
    public void delete(View view){
        Repo.r().deleteNote(currentNote.getId());
        finish();
    }

    // DOWNLOAD PIC CONNECTED TO THE NOTE BY REFERENCE
    @Override
    public void receive(byte[] bytes) {
        Bitmap databaseImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        dbImg.setImageBitmap(databaseImg);
    }

}