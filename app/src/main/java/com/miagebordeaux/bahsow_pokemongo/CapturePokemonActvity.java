package com.miagebordeaux.bahsow_pokemongo;
/**
 * BAH Mamadou Lamine
 * SOW Papa Laye
 */
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class CapturePokemonActvity extends AppCompatActivity {

    private Intent receive;
    private Integer mIdIcone;
    private String description;
    private FrameLayout mFrameLayout;
    private ImageView mCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_pokemon_actvity);

        mCapture = (ImageView) findViewById(R.id.preview_view);
        mFrameLayout = (FrameLayout) findViewById(R.id.cameraPreview);

        receive = getIntent();

        description = (String) receive.getStringExtra("info");
        mIdIcone = Integer.valueOf((String) receive.getStringExtra("icone"));


        if (description != null) {
            Log.d("receive ", "description");
        }
        if (mIdIcone != null) {
            Log.d("Image receive", "image");
        }

        mCapture.setImageResource(mIdIcone);

    }

}