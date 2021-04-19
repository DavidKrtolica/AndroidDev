package com.example.listviewapp.adapter.repo;

import com.example.listviewapp.Updateable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repo {

    private static Repo repo = new Repo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> notes = new ArrayList<>();
    private Updateable activity;

    public static Repo r() {
        return repo;
    }

    // THE START LISTENER METHOD
    public void readNotes() {
        db.collection("notes").addSnapshotListener((values, error) -> {
            notes.clear();
            for (DocumentSnapshot snap: values.getDocuments()) {
                if (snap.get("text") != null) {
                    String text = snap.get("text").toString();
                    notes.add(text);
                }
                // UPDATE THE LIST VIEW:
                // CREATE A REF TO THE MAIN ACTIVITY AND CALL UPDATE()
                activity.update(null);
            }
        });
    }

    public void setUp(Updateable a, ArrayList<String> _list) {
        activity = a;
        notes = _list;
        readNotes();
    }

    public void addNote() {
        DocumentReference ref = db.collection("notes").document();
        Map<String, String> map = new HashMap<>();
        map.put("text", "new note " + notes.size());
        ref.set(map);
        // CAN ALSO BE DONE BY:
        // db.collection("notes").add(map);
        System.out.println("Done inserting new document at: " + ref.getId());
    }

}