package com.m2dl.challenge.challengeandroid.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.m2dl.challenge.challengeandroid.Activity.MainActivity;
import com.m2dl.challenge.challengeandroid.Model.Configuration;
import com.m2dl.challenge.challengeandroid.Model.Score;
import com.m2dl.challenge.challengeandroid.R;

/**
 * Created by julien on 28/01/16.
 */

public class TakePicture extends Activity implements SurfaceHolder.Callback
{
    //a variable to store a reference to the Image View at the main.xml file
    private ImageView iv_image;
    //a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;
    //a bitmap to display the captured image
    private Bitmap bmp;
    //Camera variables
    //a surface holder
    private TextView tvScore;
    private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;
    private String score;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takepicture);

        Intent intent = getIntent();
        score = intent.getStringExtra("score");//Integer.getInteger(intent.getStringExtra("score"));
        tvScore = (TextView) findViewById(R.id.textViewScore);
        tvScore.setText("Score : "+score);

        //get the Image View at the main.xml file
        iv_image = (ImageView) findViewById(R.id.imageView);
        //get the Surface View at the main.xml file
        sv = (SurfaceView) findViewById(R.id.surfaceView);
        //Get a surface
        sHolder = sv.getHolder();
        //add the callback interface methods defined below as the Surface View callbacks
        sHolder.addCallback(this);
        //tells Android that this surface will have its data constantly replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {
        //get camera parameters
        parameters = mCamera.getParameters();
        //set camera parameters
        mCamera.setParameters(parameters);
        mCamera.startPreview();

        //sets what code should be executed after the picture is taken
        Camera.PictureCallback mCall = new Camera.PictureCallback()
        {
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                //decode the data obtained by the camera into a Bitmap
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                //set the iv_image
                //Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), bmp.getHeight(), Toast.LENGTH_SHORT).show();
                //iv_image.setImageBitmap(bmp);

                //MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "YOU_LOOSE" + new Date().getTime(), "Image du jeu");

                String pathPhoto = "gameover-"+new Date().getTime()+".png";
                File f = new File(getDir("", MODE_WORLD_WRITEABLE).getPath()+pathPhoto);
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(f);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                iv_image.setImageBitmap(BitmapFactory.decodeFile(pathPhoto));
                saveScore(f.getPath());
            }
        };

        mCamera.takePicture(null, null, mCall);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        // The Surface has been created, acquire the camera and tell it where
        // to draw the preview.
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        try {
            mCamera.setPreviewDisplay(holder);

        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //stop the preview
        mCamera.stopPreview();
        //release the camera
        mCamera.release();
        //unbind the camera from this object
        mCamera = null;
    }

    public void saveScore(String pathPhoto) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}