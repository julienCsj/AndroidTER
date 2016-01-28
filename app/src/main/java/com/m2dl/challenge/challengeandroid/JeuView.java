package com.m2dl.challenge.challengeandroid;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

/**
 * Created by Elliot on 28/01/2016.
 */
public class JeuView extends SurfaceView implements SurfaceHolder.Callback {

    private JeuThread thread;
    private Context context;
    private TextView textViewStatut;

    public JeuView(Context activityContext) {
        super(activityContext);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new JeuThread(holder, activityContext, new Handler()
        {
            @Override
            public void handleMessage(Message m)
            {
                // mStatusText.setVisibility(m.getData().getInt("viz"));
                // mStatusText.setText(m.getData().getString("text"));
            }
        });

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return thread.onTouchEvent(event);
    }

    public JeuThread getThread()
    {
        return thread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new JeuThread(getHolder(), getContext(), getHandler());
            thread.start();
        } else {
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);

        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {
            }
        }
    }
}
