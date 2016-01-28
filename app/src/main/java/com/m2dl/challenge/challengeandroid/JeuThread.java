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
    private List<Objet> objets;

    private boolean running;
    private long frameRate;
    private boolean loading;
    private int timer;

    private JeuActivity activity;
    private Context context;

    private SurfaceHolder surfaceHolder;
    private Handler handler;

    public JeuThread(SurfaceHolder holder, Context context, Handler handler) {
        this.surfaceHolder = holder;
        this.handler = handler;
        this.context = context;
        this.activity = (JeuActivity) context;

        objets = new ArrayList<Objet>();
        running = true;
        // 30 images par seconde.
        frameRate = (long) (1000 / 30);
        loading = true;
        timer = 0;
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    long start = System.currentTimeMillis();
                    doDraw(c);
                    long diff = System.currentTimeMillis() - start;

                    if (diff < frameRate)
                        Thread.sleep(frameRate - diff);
                }
            } catch (InterruptedException e) {
            }
            finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    protected void doDraw(Canvas canvas) {
        timer++;
        if (timer >= 100) {
            GenerationObjet gen = new GenerationObjet(context);
            objets.add(gen.genererObjetAleatoire());
            timer = 0;
        }

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), new Paint(Color.BLACK));
        for (int i = 0; i < objets.size(); i++) {
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),
                    objets.get(i).getSkin()), objets.get(i).getX().intValue(), objets.get(i).getY().intValue(), null);
            objets.get(i).bouger();
        }
        for (int i = 0; i < objets.size(); i++) {
            if (objets.get(i).getY() > canvas.getHeight()) {
                objets.remove(i);
            }
        }
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
