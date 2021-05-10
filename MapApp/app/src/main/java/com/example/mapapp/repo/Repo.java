package com.example.mapapp.repo;

import com.example.mapapp.Updateable;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {

    private static Repo repo = new Repo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Updateable activity;
    public List<GeoPoint> locations = new ArrayList<>();

    private Repo(){}

    public static Repo r() {
        return repo;
    }

    // THE START LISTENER METHOD
    public void readLocations() {
        db.collection("locations").addSnapshotListener((values, error) -> {
            for (DocumentSnapshot snap: values.getDocuments()) {
                GeoPoint gp = snap.getGeoPoint("position");
                if (gp != null) {
                    locations.add(gp);
                }
            }
            activity.update(null);
        });
    }

    public void setUpRepo(Updateable activity) {
        this.activity = activity;
        readLocations();
    }

    public List<GeoPoint> getLocations() {
        return locations;
    }

    public void addMarker(LatLng position) {
        DocumentReference ref = db.collection("locations").document();
        Map<String, GeoPoint> map = new HashMap<>();
        GeoPoint gp = new GeoPoint(position.latitude, position.longitude);
        map.put("position", gp);
        ref.set(map);
    }
}
