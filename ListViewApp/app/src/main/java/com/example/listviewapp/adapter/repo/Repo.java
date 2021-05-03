package com.example.listviewapp.adapter.repo;

import android.graphics.Bitmap;

import com.example.listviewapp.TaskListener;
import com.example.listviewapp.Updateable;
import com.example.listviewapp.model.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repo {

    private static Repo repo = new Repo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<Note> notes = new ArrayList<>();
    private Updateable activity;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public static Repo r() {
        return repo;
    }

    // THE START LISTENER METHOD
    public void readNotes() {
        db.collection("notes").addSnapshotListener((values, error) -> {
            notes.clear();
            for (DocumentSnapshot snap: values.getDocuments()) {
                if (snap.get("text") != null) {
                    Object text = snap.get("text");
                    notes.add(new Note(snap.getId(), text.toString()));
                }
                // UPDATE THE LIST VIEW:
                // CREATE A REF TO THE MAIN ACTIVITY AND CALL UPDATE()
                activity.update(null);
            }
        });
    }

    public Note getNoteWith(String id){
        for(Note note : notes){
            if(note.getId().equals(id)){
                return note;
            }
        }
        return null;
    }

    public void setUp(Updateable a, ArrayList<Note> _list) {
        activity = a;
        notes = _list;
        readNotes();
    }

    public void addNote(String new_note) {
        DocumentReference ref = db.collection("notes").document();
        Note note = new Note(ref.getId(), new_note);
        Map<String, String> map = new HashMap<>();
        map.put("id", note.getId());
        map.put("text", note.getText() + notes.size());
        ref.set(map);
        // CAN ALSO BE DONE BY:
        // db.collection("notes").add(map);
        System.out.println("Done inserting new document at: " + ref.getId());
    }

    public void updateNote(Note note) {
        DocumentReference ref = db.collection("notes").document(note.getId());
        Map<String,String> map = new HashMap<>();
        map.put("id", note.getId());
        map.put("text", note.getText());
        ref.set(map);
        System.out.println("Done updating document " + ref.getId());
    }

    public void uploadBitmap(Note currentNote,  Bitmap bitmap) {
        updateNote(currentNote);
        StorageReference ref = storage.getReference(currentNote.getId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ref.putBytes(baos.toByteArray()).addOnCompleteListener(snap -> {
            System.out.println("OK to upload " + snap);
        }).addOnFailureListener(exception -> {
            System.out.println("failure to upload " + exception);
        });
    }

    public void deleteNote(String id){
        DocumentReference docRef = db.collection("notes").document(id);
        docRef.delete();
    }

    public void downloadBitmap(String id, TaskListener taskListener) {
        StorageReference ref = storage.getReference(id);
        int max = 6000 * 6000;
        ref.getBytes(max).addOnSuccessListener(bytes -> {
            taskListener.receive(bytes);
            System.out.println("Download OK");
        }).addOnFailureListener(ex -> {
            System.out.println("error in download " + ex);
        });
    }

}