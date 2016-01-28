package com.m2dl.challenge.challengeandroid.Activity;

import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
=======
>>>>>>> 80900b96898ce49f0aa532047f7310e82f426535
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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

        Intent takePhoto = new Intent(this, TakePicture.class);
        startActivity(takePhoto);

        Intent intent = new Intent(this, JeuActivity.class);
        startActivity(intent);
    }
}
