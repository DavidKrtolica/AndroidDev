package com.example.mapapp;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.example.mapapp.repo.Repo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Updateable {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // CALL REPO CLASS
        Repo.r().setUpRepo(this);
        mMap.setOnMapLongClickListener(latLng -> {
            System.out.println("You long pressed" + latLng);
            // ADDING A MARKER ON LONG PRESS
            askForText(latLng);
        });

    }

    private void addMarker(LatLng position, String msg) {
        mMap.addMarker(new MarkerOptions().position(position).title(msg));
        Repo.r().addMarker(position);
    }

    public void askForText(LatLng position) {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                                .setTitle("New marker")
                                .setMessage("Add text:")
                                .setView(editText)
                                .setPositiveButton("Add", (d, whatBtn) -> {
                                    addMarker(position, editText.getText().toString());
                                })
                                .setNegativeButton("Cancel", null)
                                .create()
                            .show();
    }

    @Override
    public void update(Object object) {
        mMap.clear();
        for(GeoPoint geoPoint: Repo.r().getLocations()) {
            LatLng latLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Standard"));
        }
    }

}