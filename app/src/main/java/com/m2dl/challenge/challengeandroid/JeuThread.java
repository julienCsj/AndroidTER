package com.m2dl.challenge.challengeandroid;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.m2dl.challenge.challengeandroid.Activity.JeuActivity;
import com.m2dl.challenge.challengeandroid.Service.GenerationObjet;

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
        float gyroscopeZ = this.activity.getJeuView().getGyroscopeZ();
        if (gyroscopeZ > 3) {
            this.activity.getJeuView().moveDeplacementX(-3);
        } else {
            if (gyroscopeZ > 2) {
                this.activity.getJeuView().moveDeplacementX(-2);
            } else {
                if (gyroscopeZ > 1) {
                    this.activity.getJeuView().moveDeplacementX(-1);
                } else {
                    if (gyroscopeZ > 0) {
                        this.activity.getJeuView().moveDeplacementX(0);
                    } else {
                        if (gyroscopeZ > -1) {
                            this.activity.getJeuView().moveDeplacementX(1);
                        } else {
                            if (gyroscopeZ > -2) {
                                this.activity.getJeuView().moveDeplacementX(2);
                            } else {
                                if (gyroscopeZ > -3) {
                                    this.activity.getJeuView().moveDeplacementX(3);
                                }
                            }
                        }
                    }
                }
            }
        }

        Log.i("ter.jeuthread", String.format("gyro %f - value defined %d", this.activity.getJeuView().getGyroscopeZ(), this.activity.getJeuView().getDeplacementX()));

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


    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning() {
        return running;
    }
}
