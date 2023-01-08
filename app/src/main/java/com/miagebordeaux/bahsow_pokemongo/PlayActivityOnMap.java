package com.miagebordeaux.bahsow_pokemongo;

/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlayActivityOnMap extends AppCompatActivity implements
        OnMapReadyCallback {

    GoogleMap gMap;
    FrameLayout map;
    private Intent mIntentReceiv;
    private String mAvatarDuJoueur;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private ImageButton  mTakeScreen;
    private static final int REQUEST_CODE = 101;
    private boolean permissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_on_map);


        mIntentReceiv = getIntent();
        mAvatarDuJoueur = new String(mIntentReceiv.getStringExtra("avatar"));
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        this.mTakeScreen = (ImageButton) findViewById(R.id.take);

        map = findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.gMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.gMap.setMyLocationEnabled(true);
        this.gMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.gMap.getUiSettings().setCompassEnabled(true);

        //Place l'icone de l'avatar sur la position de l'utilisateur
        UtilsSingletonForPlayActivityOnMap.getUtilsInstance()
                .getAndInitialiseMyPosition(this, PlayActivityOnMap.this,
                REQUEST_CODE, mFusedLocationProviderClient, getResources(),
                gMap, mAvatarDuJoueur, mTakeScreen, 220, 220);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    UtilsSingletonForPlayActivityOnMap.getUtilsInstance()
                            .getAndInitialiseMyPosition(this,PlayActivityOnMap.this,
                            REQUEST_CODE,mFusedLocationProviderClient,getResources(),
                            gMap,mAvatarDuJoueur, mTakeScreen);
                break;
        }
    }
}