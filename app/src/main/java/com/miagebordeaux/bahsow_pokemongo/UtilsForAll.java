package com.miagebordeaux.bahsow_pokemongo;

/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class UtilsForAll {


    public  UtilsForAll () {
    }
    /**
     * Renvoie une image rédimensionnée qui servira d'icone du joueur sur la carte
     * @param avatarDuJoueur : le nom de l'image de l'avatar
     * @param h : la hauteur de l'image
     * @param w : la largeur de l'image
     * @return : une image Bitmap
     */
    public Bitmap selectAvatarChoosen (String avatarDuJoueur, Resources resources, int h, int w) {
        BitmapDrawable bt = bt = (BitmapDrawable) resources.getDrawable(getIdIcone (avatarDuJoueur));
        return Bitmap.createScaledBitmap(bt.getBitmap(), h, w, false);
    }
    /**
     *
     * @param gmap
     * @param loc
     * @param title
     * @param bmp
     * @param ctrlFlat
     * @return
     */
    public Marker createMarker (GoogleMap gmap, LatLng loc, String title, BitmapDescriptor bmp,
                                boolean ctrlFlat) {
        Marker mark = gmap.addMarker(new MarkerOptions()
                .position(loc).title(title).snippet("Longitude : "+loc.longitude+", Latitude : "
                        +loc.latitude).icon(bmp).flat(ctrlFlat)
        );
        return mark;
    }

    /***
     *
     * @param gmap
     * @param loc
     * @param ctrlZoom
     * @param setComp
     * @param zoom
     */
    public  void cntrlMveCameraZoomCompass (GoogleMap gmap, LatLng loc,boolean ctrlZoom,
                                            boolean setComp, float zoom) {
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
        gmap.getUiSettings().setZoomControlsEnabled(ctrlZoom);
        gmap.getUiSettings().setCompassEnabled(setComp);
    }

    /**
     *
     * @param avatarDuJoueur
     * @return
     */
    int getIdIcone (String avatarDuJoueur) {
        int id = 0;
        if (avatarDuJoueur.compareTo("Joueur_1") == 0)
            id = R.drawable.pok_1;
        else if (avatarDuJoueur.compareTo("Joueur_2") == 0)
            id = R.drawable.pok_2;
        else if (avatarDuJoueur.compareTo("Joueur_3") == 0)
            id = R.drawable.pok_3;
        else if (avatarDuJoueur.compareTo("Joueur_4") == 0)
            id = R.drawable.pok_4;
        else if (avatarDuJoueur.compareTo("Joueur_5") == 0)
            id = R.drawable.pok_5;
        else if (avatarDuJoueur.compareTo("Sprite_1") == 0)
            id = R.drawable.sprite_1;
        else if (avatarDuJoueur.compareTo("Sprite_2") == 0)
            id = R.drawable.sprtie_2;
        else if (avatarDuJoueur.compareTo("Sprite_3") == 0)
            id = R.drawable.sprite_3;
        else if (avatarDuJoueur.compareTo("Sprite_4") == 0)
            id = R.drawable.sprite_4;
        else if (avatarDuJoueur.compareTo("Sprite_5") == 0)
            id = R.drawable.sprite_5;
        else if (avatarDuJoueur.compareTo("Sprite_6") == 0)
            id  = R.drawable.sprite_6;
        else id = R.drawable.pok_6;

        return id;
    }
    /**
     *
     * @param loc
     * @param resources
     * @param l_w
     */
    List <Pokemon> initSpritesListe (LatLng loc, Resources resources, String avatar, int ...l_w) {

        List <Pokemon> mpokemons = new ArrayList<Pokemon>();

        mpokemons.add(new Pokemon(loc.latitude, loc.longitude,"Player",
                        selectAvatarChoosen(avatar,
                                resources,l_w[0] + 100, l_w[1] + 100),false, getIdIcone (avatar)));

        mpokemons.add(new Pokemon(loc.latitude + 0.003 , loc.longitude - 0.021,
                "Sprite 1",
               selectAvatarChoosen("Sprite_1", resources,l_w[0] + 25, l_w[1] + 25), true,getIdIcone ("Sprite_1")));

        mpokemons.add(new Pokemon(loc.latitude - 0.075 , loc.longitude - 0.022,
                "Sprite 2",
                        selectAvatarChoosen("Sprite_2", resources,l_w[0] + 25, l_w[1] + 25), true,getIdIcone ("Sprite_2")));

        mpokemons.add(new Pokemon(loc.latitude - 0.05 , loc.longitude - 0.023,
                "Sprite 3",
                        selectAvatarChoosen("Sprite_3", resources,l_w[0] + 25, l_w[1] + 25), true,getIdIcone ("Sprite_3")));

        mpokemons.add(new Pokemon(loc.latitude - 0.023 , loc.longitude - 0.024,
                "Sprite 4",
                selectAvatarChoosen("Sprite_4", resources,l_w[0] + 25, l_w[1]+ 25), true,getIdIcone ("Sprite_4")));

        return mpokemons;
    }

    /**
     *
     * @param gmap
     * @param mPokemons
     * @return
     */
    public List<Marker> addPokemonsToMArkerLists (GoogleMap gmap, List<Pokemon> mPokemons) {
        List<Marker> mMarkerPokemons = new ArrayList<Marker>();
        for (Pokemon p: mPokemons) {
            if (p.isP()) {
                mMarkerPokemons.add(gmap.addMarker(p.getMarkOptions()));
                mMarkerPokemons.get(mMarkerPokemons.size() - 1).setDraggable(false);
            }
        }
        return  mMarkerPokemons;
    }
}
