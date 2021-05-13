package com.example.snapapp.repo;

import androidx.annotation.NonNull;

import com.example.snapapp.TaskListener;
import com.example.snapapp.Updateable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyRepo {

    private static MyRepo repo = new MyRepo();
    public ArrayList<String> imgRefs = new ArrayList<>();
    private Updateable activity;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    public static MyRepo r() {
        return repo;
    }

    public void readImgRefs() {
        StorageReference listRef = storage.getReference();
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            imgRefs.add(item.getPath());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error occured! - " + e);
                    }
                });
        activity.update(null);
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

    public void setUp(Updateable a, ArrayList<String> _list) {
        activity = a;
        imgRefs = _list;
        readImgRefs();
    }

}
