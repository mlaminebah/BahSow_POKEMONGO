package com.miagebordeaux.bahsow_pokemongo;
/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class UtilsSingletonForPlayActivityOnMap {
    private static UtilsSingletonForPlayActivityOnMap instance = null;
    private Handler mHandler;
    private List<Pokemon> mPokemons;
    private UtilsForAll mUtilsForAll;
    private static Marker mMarkerPalyer;
    private List <Marker>  mMarkerPokemons;
    private List <Pokemon> capturedPokemon;

    private UtilsSingletonForPlayActivityOnMap() {

        mPokemons = new ArrayList<>();
        mMarkerPokemons = new ArrayList<Marker>();
        mUtilsForAll = new UtilsForAll();
        capturedPokemon = new ArrayList<Pokemon>();

    }

    //static Marker markerPlayer;
    public static UtilsSingletonForPlayActivityOnMap getUtilsInstance () {
        if (instance == null) {
            instance = new UtilsSingletonForPlayActivityOnMap();
        }
        return  instance;
    }
    /**
     *
     * @param context
     * @param activity
     * @param REQUEST_CODE
     * @param mFusedLocationProviderClient
     * @param resources
     * @param gmap
     * @param avatar
     * @param l_w
     */
    public void getAndInitialiseMyPosition(Context context, AppCompatActivity activity,
                                           int REQUEST_CODE, FusedLocationProviderClient mFusedLocationProviderClient,
                                           Resources resources, GoogleMap gmap, String avatar,
                                           ImageButton takeScreen, int... l_w) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String [] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },REQUEST_CODE);
            //return null;
        }
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        mFusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY ,
                cancellationTokenSource.getToken()).addOnSuccessListener(activity,
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                            //Ajout des sprites sur la carte
                            mPokemons = mUtilsForAll.initSpritesListe(loc, resources, avatar, l_w[0], l_w[1]);
                            mMarkerPokemons = mUtilsForAll.addPokemonsToMArkerLists(gmap, mPokemons);

                            //Ajout du joueur sur la carte
                            for (Pokemon p: mPokemons) {
                                if (!p.isP()) {
                                    mMarkerPalyer = gmap.addMarker(p.getMarkOptions());
                                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f));
                                    gmap.getUiSettings().setZoomControlsEnabled(true);
                                }
                            }
                            //Lorsqu'on clique sur la carte , le joueur y va sur cette position
                            clickOnMap(gmap, mMarkerPalyer);
                            //Lorsqu'on clique sur un marker
                            clickOnMarker(gmap, mMarkerPalyer);

                            //Lorsque je clique pour prendre en photo un pokemon
                            takeScreenOfPokemon(gmap, activity, mMarkerPalyer, takeScreen, mMarkerPokemons);
                        }
                    }
                });
    }

    /**
     *
     * @param gmap
     * @param markerPalyer
     */
    private  void clickOnMap (GoogleMap gmap, Marker markerPalyer) {
        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                changePositionPlayer (mMarkerPalyer, latLng);

                Log.d ("After mooving ", String.valueOf(mMarkerPalyer.getPosition().longitude + " " +
                        mMarkerPalyer.getPosition().latitude));
            }
        });
    }

    /**
     *
     * @param gmap
     * @param markerPalyer
     */
    private  void clickOnMarker (GoogleMap gmap, Marker markerPalyer) {
        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                changePositionPlayer(mMarkerPalyer, marker.getPosition());
                return false;
            }
        });
    }

    /**
     *
     * @param gmap
     * @param markerPalyer
     */
    private  void takeScreenOfPokemon (GoogleMap gmap, Context context, Marker markerPalyer, ImageButton take, List<Marker> markerPokemons) {
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d ("Capture ","Sprite");
                Marker pokemonToThisPosition = positionMarkerIsPokemonMarker (markerPalyer, markerPokemons);
                if (pokemonToThisPosition != null) {
                    try {
                        //Afficher le sprite sur le flux de la caméra avec ses informations
                        //Il faut démarrer une activité
                        Intent it = new Intent(context, CapturePokemonActvity.class);

                        Pokemon poke = getPokemonOnThisPosition(pokemonToThisPosition
                                .getPosition(), mPokemons);
                        //poke.setTrouve(true);
                        capturedPokemon.add(poke);
                        //On récupére le pokemon correspoondant pour le transmettre à la nouvelle activité
                        //mPokemosCaptured.add( new PokemonParcelable(getPokemonOnThisPosition(pokemonToThisPosition
                          //      .getPosition(), mPokemons)));

                        it.putExtra("info", poke.toString());
                        it.putExtra("icone", String.valueOf(poke.getIdCone()));

                        context.startActivity(it);

                        pokemonToThisPosition.remove();

                    } catch (Exception e) {
                        Log.e("CliqueMarker", "Erro: " + e.getMessage());
                    }

                } else {
                    Log.d ("Capture ","Pas de pokemon emplacement");
                    Toast.makeText(context, "Oups sur cette position Pas de pokemon ici ", Toast.LENGTH_LONG);
                }
            }
        });
    }

    /**
     *
     * @param markerPalyer
     * @param markerPokemon
     * @return
     */
    public double getDistancePkm (Marker markerPalyer, Marker markerPokemon) {
        Location locationMarkerPlayer = new Location(markerPalyer.getTitle());
        locationMarkerPlayer.setLatitude(markerPalyer.getPosition().latitude);
        locationMarkerPlayer.setLongitude(markerPalyer.getPosition().longitude);

        Location locationMarkerPokemon = new Location(markerPokemon.getTitle());
        locationMarkerPokemon.setLatitude(markerPokemon.getPosition().latitude);
        locationMarkerPokemon.setLongitude(markerPokemon.getPosition().longitude);

        return locationMarkerPlayer.distanceTo(locationMarkerPokemon);
    }
    /**
     *
     * @return
     */
    private Marker positionMarkerIsPokemonMarker (Marker markerPalyer, List <Marker> pokemonsList) {
        Marker pokemon = null;
        int i = 0;
        double distance = 0;
        while (i < pokemonsList.size()) {
            distance = getDistancePkm (markerPalyer, pokemonsList.get(i));
            Log.d ("Distance ", String.valueOf(distance));
            if (distance >= 0.0 && distance <= 0.9) {
                pokemon = pokemonsList.get(i);
                Log.d ("Trouvé ",pokemon.getTitle());
            }
            i += 1;
        }
        return  pokemon;
    }

    /**
     *
     * @param location
     * @param pokemons
     * @return
     */
    private  Pokemon getPokemonOnThisPosition (LatLng location, List<Pokemon> pokemons) {
        Pokemon pokemon = null;
        Location loca1 = new Location("Pok 1");
        Location loc2 = new Location("Pok 2");


        loca1.setLatitude(location.latitude);
        loca1.setLongitude(location.longitude);

        for (Pokemon p :  pokemons) {
            loc2.setLatitude(p.getLatitude());
            loc2.setLongitude(p.getLongitude());
            double distance = loca1.distanceTo(loc2);
            if (distance >= 0.0 && distance <= 0.9 && p.isP()) {

                pokemon = p;
            }
        }
        return  pokemon;
    }
    /**
     *
     * @param markerP
     * @param startLatLong
     * @param destLatLong
     * @param bearing
     * @param gmap
     */
    private  void changePositionPlayerM (final Marker markerP, LatLng startLatLong, LatLng destLatLong, final float bearing, GoogleMap gmap) {
        //markerP.setPosition(destLatLong);
        if (markerP == null) {
            return;
        }

        ValueAnimator animation = ValueAnimator.ofFloat(0f, 100f);
        final float[] previousStep = {0f};

        double deltaLatitude = destLatLong.latitude - markerP.getPosition().latitude;
        double deltaLongitude = destLatLong.longitude - markerP.getPosition().longitude;
        animation.setDuration(5000);
        animation.addUpdateListener(animation1 -> {
            float deltaStep = (Float) animation1.getAnimatedValue() - previousStep[0];
            previousStep[0] = (Float) animation1.getAnimatedValue();
            LatLng currentPosition = new LatLng(markerP.getPosition().latitude + deltaLatitude * deltaStep * 1 / 100,
                    markerP.getPosition().longitude + deltaStep * deltaLongitude * 1 / 100);
            markerP.setPosition(currentPosition);

        });
        animation.start();
    }

    /**
     *
     * @param marker
     * @param latlong
     */
    void changePositionPlayer (Marker marker, LatLng latlong) {

        int numDeltas = 100;
        int delay = 10; //milliseconds
        int i = 0;
        double [] position = new  double[] {marker.getPosition().latitude,
        marker.getPosition().longitude};
        double deltaLat = (latlong.latitude - position[0])/numDeltas;
        double deltaLng = (latlong.longitude - position[1])/numDeltas;

        while (i != numDeltas) {
            position[0] += deltaLat;
            position[1] += deltaLng;
            marker.setPosition (new LatLng(position[0], position [1]));
            i ++;

        }
    }

}
