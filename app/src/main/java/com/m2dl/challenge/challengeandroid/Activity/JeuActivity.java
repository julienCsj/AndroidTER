package com.m2dl.challenge.challengeandroid.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.m2dl.challenge.challengeandroid.JeuView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class JeuActivity extends AppCompatActivity  implements SensorEventListener {

    private JeuView jeuView;
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private float orientationZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAccelerometer();
    }

    @Override
    protected void onStart() {
        FrameLayout layout = new FrameLayout(this);
        jeuView = new JeuView(this);
        layout.addView(jeuView);
        setContentView(layout);
        super.onStart();
    }

    @Override
    protected void onStop() {
        try {
            jeuView.getThread().setRunning(false);
            jeuView = null;
        }
        catch (NullPointerException e) {}
        super.onStop();
    }


    public void setUpAccelerometer () {

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            // Il y a au moins un accéléromètre
        } else {
            Toast.makeText(this, "No accelerometer detected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        // unregister the sensor (désenregistrer le capteur)
        sensorManager.unregisterListener(this, gyroscope);
        super.onPause();
    }

    @Override
    protected void onResume() {
        /* Ce qu'en dit Google&#160;dans le cas du gyroscope :
         * «Ce n'est pas nécessaire d'avoir les évènements des capteurs à un rythme trop rapide.
         * En utilisant un rythme moins rapide (SENSOR_DELAY_UI), nous obtenons un filtre
         * automatique de bas-niveau
         * Un autre bénéfice étant que l'on utilise moins d'énergie et de CPU»
         */
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    /********************************************************************/
    /** SensorEventListener**********************************************/
    /********************************************************************/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Rien à faire la plupart du temps
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Récupérer les valeurs du capteur
        float x, y, z;
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            orientationZ += z;
            //Log.i("ter.VerreView", String.format("[%f, %f, %f] - orientationY %f", x, y, z, orientationZ));
        }
    }

    public JeuView getJeuView() {
        return jeuView;
    }
}
