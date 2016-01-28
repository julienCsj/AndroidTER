package com.m2dl.challenge.challengeandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.m2dl.challenge.challengeandroid.JeuView;
import com.m2dl.challenge.challengeandroid.Service.TakePicture;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class JeuActivity extends AppCompatActivity  implements SensorEventListener {

    private static final float EPSILON = 1;
    private JeuView jeuView;
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private float orientationZ = 0;


    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;

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
        jeuView.getThread().start();
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
     // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            // Calculate the angular speed of the sample
            float omegaMagnitude = ((Double)Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ)).floatValue();

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
            float sinThetaOverTwo = (float)Math.sin(thetaOverTwo);
            float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
            deltaRotationVector[0] = sinThetaOverTwo * axisX;
            deltaRotationVector[1] = sinThetaOverTwo * axisY;
            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
            deltaRotationVector[3] = cosThetaOverTwo;
            //Log.i("ter.VerreView", String.format("[%f, %f, %f] - orientationY %f", deltaRotationVector[0], deltaRotationVector[1], deltaRotationVector[2], deltaRotationVector[3]));
            orientationZ += deltaRotationVector[2];
            jeuView.setGyroscope(orientationZ);
        }
        timestamp = event.timestamp;
        float[] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;
        //orientation = orientation * deltaRotationMatrix
    }
        /*
        // Récupérer les valeurs du capteur
        float x, y, z;
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            orientationZ += z;
            jeuView.setGyroscope(orientationZ);
            //Log.i("ter.VerreView", String.format("[%f, %f, %f] - orientationY %f", x, y, z, orientationZ));
        }
        */

    public JeuView getJeuView() {
        return jeuView;
    }

    public void launchGameOver(int score) {
        Intent intent = new Intent(this, TakePicture.class);
        intent.putExtra("score", score+"");
        startActivity(intent);
    }
}
