package com.miagebordeaux.bahsow_pokemongo;
/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String mAvatarDuJoueur = new String ("Joueur_1");
    private Button mPlayBtn;
    private ImageView mJ1, mJ2, mJ3, mJ4, mJ5, mJ6;
    private Intent mOpenGameOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialiser les ressources
        initImagesBtn ();

        //attaché l'objet à un événement
        listen ();

    }

    private void initImagesBtn () {
        mPlayBtn = (Button) findViewById(R.id.idPlay);
        mJ1 = (ImageView) findViewById(R.id.Joueur_1);
        mJ2 = (ImageView) findViewById(R.id.Joueur_2);
        mJ3 = (ImageView) findViewById(R.id.Joueur_3);
        mJ4 = (ImageView) findViewById(R.id.Joueur_4);
        mJ5  = (ImageView) findViewById(R.id.Joueur_5);
        mJ6 = (ImageView) findViewById(R.id.Joueur_6);
    }

    private void listen () {
        mPlayBtn.setOnClickListener(this);
        mJ1.setOnClickListener(this);
        mJ2.setOnClickListener(this);
        mJ3.setOnClickListener(this);
        mJ4.setOnClickListener(this);
        mJ5.setOnClickListener(this);
        mJ6.setOnClickListener(this);
    }
    //Si on clique sur un bouton
    @Override
    public void onClick(View view) {
       // Button b = (Button) view;
        switch (view.getId()) {
            case R.id.idPlay :
                UtilSingletonForMain.getUtilsInstForMain()
                        .play(mAvatarDuJoueur,mOpenGameOnMap, MainActivity.this);
            default:
                UtilSingletonForMain.getUtilsInstForMain().
                        selectPlayer(view, mAvatarDuJoueur, mOpenGameOnMap,MainActivity.this);
                break;

        }
    }
}