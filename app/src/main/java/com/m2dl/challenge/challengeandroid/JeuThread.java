package com.m2dl.challenge.challengeandroid;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.m2dl.challenge.challengeandroid.Activity.JeuActivity;
import com.m2dl.challenge.challengeandroid.Model.Objet;
import com.m2dl.challenge.challengeandroid.Service.GenerationObjet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elliot on 28/01/2016.
 */
public class JeuThread extends Thread {
    private boolean running;
    private long frameRate;
    private boolean loading;
    private int timer;

    private JeuActivity activity;
    private Context context;

    private Handler handler;

    public JeuThread(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
        this.activity = (JeuActivity) context;

        running = true;
        // 30 images par seconde.
        frameRate = (long) (1000 / 30);
        loading = true;
        timer = 0;
    }

    @Override
    public void run() {
        while (running) {
            try {

                long start = System.currentTimeMillis();
                doDraw();
                long diff = System.currentTimeMillis() - start;

                if (diff < frameRate)
                    Thread.sleep(frameRate - diff);
            } catch (InterruptedException e) {
            }
        }
    }

    protected void doDraw() {
        timer++;
        if (timer >= 100) {
            GenerationObjet gen = new GenerationObjet(context);
            this.activity.getJeuView().addObjet(gen.genererObjetAleatoire());
            timer = 0;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update TextView here!
                forceDraw();
            }
        });

    }

    private void forceDraw() {
        this.activity.getJeuView().invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        return true;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning() {
        return running;
    }
}
