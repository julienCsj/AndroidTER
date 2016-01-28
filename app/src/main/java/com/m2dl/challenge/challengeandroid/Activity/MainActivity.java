package com.m2dl.challenge.challengeandroid.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.m2dl.challenge.challengeandroid.Model.Configuration;
import com.m2dl.challenge.challengeandroid.Model.Difficulte;
import com.m2dl.challenge.challengeandroid.R;
import com.m2dl.challenge.challengeandroid.Service.TakePicture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

        Configuration confActuelle = new Select().from(Configuration.class).orderBy("date DESC").executeSingle();
        if (confActuelle == null) {
            confActuelle = new Configuration("Anonyme", Difficulte.FACILE, new Date());
            confActuelle.save();
        }
    }

    public void runGame(View v) {
        Intent intent = new Intent(this, JeuActivity.class);
        startActivity(intent);
    }

    public void runScore(View v) {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void runPrefs(View v) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }
}
