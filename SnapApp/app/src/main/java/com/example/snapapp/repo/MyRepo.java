package com.example.snapapp.repo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.snapapp.TaskListener;
import com.example.snapapp.Updateable;
import com.example.snapapp.model.ImageRef;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRepo {

    private static MyRepo repo = new MyRepo();
    public ArrayList<ImageRef> imgRefs = new ArrayList<>();
    private Updateable activity;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static MyRepo r() {
        return repo;
    }

    // METHOD FOR READING THE DOCUMENTS FROM "imgRefs" COLLECTION IN FIREBASE
    // THIS COLLECTION IS USED TO KEEP TRACK OF ALL OF THE REFERENCES TO THE ACTUAL
    // PICTURES SAVED TO THE FIRESTORAGE
    public void readImgRefs() {
        db.collection("imgRefs").addSnapshotListener((values, error) -> {
            imgRefs.clear();
            for (DocumentSnapshot snap: values.getDocuments()) {
                if (snap.get("ref") != null) {
                    Object ref = snap.get("ref");
                    imgRefs.add(new ImageRef(snap.getId(), ref.toString()));
                }
                // UPDATE THE LIST VIEW:
                // CREATE A REF TO THE MAIN ACTIVITY AND CALL UPDATE()
                activity.update(null);
            }
        });
    }

    // ADDING A IMAGE REFERENCE DOCUMENT WHEN AN IMAGE/BITMAP IS BEING UPLOADED
    public void addImgRef(String new_ref) {
        DocumentReference ref = db.collection("imgRefs").document();
        ImageRef imgRef = new ImageRef(ref.getId(), new_ref);
        Map<String, String> map = new HashMap<>();
        map.put("id", imgRef.getId());
        map.put("ref", imgRef.getRef());
        ref.set(map);
        System.out.println("Done inserting new document at: " + ref.getId());
    }

    // DELETING THE REFERENCE ONCE USER SEES THE SNAP
    public void deleteRef(String id){
        DocumentReference docRef = db.collection("imgRefs").document(id);
        docRef.delete();
    }

    // UPLOADING THE ACTUAL EDITED IMAGE TO FIRESTORAGE
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

    // DOWNLOADING IMAGE FROM FIRESOTRAGE BY USING ITS ID (IN THIS CASE THE REF NAME)
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

    // DELETING THE ACTUAL IMAGE BY AGAIN USING REF NAME
    public void deleteDbImg(String imgName) {
        StorageReference ref = storage.getReference(imgName);
        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Deleted successfully!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Oh! Looks like an error occured!");
            }
        });
    }

    // SETTING UP THE REPO, METHOD USED IN CHAT ACTIVITY CLASS
    public void setUp(Updateable a, ArrayList<ImageRef> _list) {
        activity = a;
        imgRefs = _list;
        readImgRefs();
    }
}
