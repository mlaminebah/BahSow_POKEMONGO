package com.miagebordeaux.bahsow_pokemongo;

/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UtilSingletonForMain {
    private static UtilSingletonForMain instance = null;
    private UtilSingletonForMain() {}

    public static UtilSingletonForMain getUtilsInstForMain () {
        if (instance == null) {
            instance = new UtilSingletonForMain();
        }
        return  instance;
    }

    public String choiceAvatar (View op) {
        String nom = new String ("Joueur_1");
        switch (op.getId()) {
            case R.id.Joueur_2: nom =  new String("Joueur_2"); break;
            case R.id.Joueur_3: nom =  new String("Joueur_3"); break;
            case R.id.Joueur_4: nom =  new String("Joueur_4"); break;
            case R.id.Joueur_5: nom =  new String("Joueur_5"); break;
            case R.id.Joueur_6: nom =  new String("Joueur_6"); break;
            default: break;
        }
        return nom;
    }
    //Sélectionner l'avatar souhaité
    public void selectPlayer (View op, String mAvatarDuJoueur,
                              Intent mOpenGameOnMap, AppCompatActivity ctx) {
        mAvatarDuJoueur = UtilSingletonForMain.getUtilsInstForMain().choiceAvatar(op);
        //Lancement du jeu
        play(mAvatarDuJoueur, mOpenGameOnMap, ctx);
    }

    //Lancer le jeu
    public void play (String avatar, Intent mOpenGameOnMap, AppCompatActivity ctx) {
        mOpenGameOnMap = new Intent(ctx,
                PlayActivityOnMap.class);

        //lancement de l'activité qui charge la carte
        mOpenGameOnMap.putExtra("avatar", avatar);
        ctx.startActivity(mOpenGameOnMap);
    }
}
