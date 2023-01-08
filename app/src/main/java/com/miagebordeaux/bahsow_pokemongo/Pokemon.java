package com.miagebordeaux.bahsow_pokemongo;
/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */
import android.graphics.Bitmap;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.Objects;

public class Pokemon {
    private LatLng latLngPok;
    private double latitude;
    private double longitude;
    private String snipest;
    private String titre;
    private Bitmap icon;
    private int idCone;
    private MarkerOptions markOptions;
    private boolean trouve;
    private boolean isP;
    private int ico;

    public Pokemon(double latitude, double longitude, String titre, Bitmap icon, boolean isP, int ico) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.snipest = new String ("Lat "+latitude+", Long "+longitude);
        this.titre = titre;
        this.icon = icon;
        this.trouve = false;
        this.isP = isP;
        this.latLngPok = new LatLng(latitude, longitude);

        this.idCone = ico;

        this.markOptions = new MarkerOptions()
                .position(latLngPok)
                .icon(BitmapDescriptorFactory.fromBitmap(icon));

        this.markOptions.title(titre).snippet(this.snipest);

    }

    public int getIdCone() {
        return idCone;
    }

    public void setIdCone(int idCone) {
        this.idCone = idCone;
    }

    public boolean isP() {
        return isP;
    }

    public LatLng getLatLngPok() {
        return latLngPok;
    }

    public String getSnipest() {
        return snipest;
    }

    public String getTitre() {
        return titre;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public MarkerOptions getMarkOptions() {
        return markOptions;
    }

    public boolean isTrouve() {
        return trouve;
    }

    public void setLatLngPok(LatLng latLngPok) {
        this.latLngPok = latLngPok;
    }

    public void setSnipest(String snipest) {
        this.snipest = snipest;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setMarkOptions(MarkerOptions markOptions) {
        this.markOptions = markOptions;
    }



    @Override
    public String toString() {
        return "Pokemon{" +
                snipest+'\''+
                ", titre='" + titre + "}";
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        this.snipest = new String ("Lat "+latitude+", Long "+longitude);
        this.latLngPok = new LatLng( latitude, this.longitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        this.snipest = new String ("Lat "+latitude+", Long "+longitude);
        this.latLngPok = new LatLng( this.latitude, longitude);
    }

    public void setTrouve(boolean trouve) {
        this.trouve = trouve;
    }

}
