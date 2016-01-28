package com.m2dl.challenge.challengeandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.m2dl.challenge.challengeandroid.Model.Objet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elliot on 28/01/2016.
 */
public class JeuView extends SurfaceView implements SurfaceHolder.Callback {

    private JeuThread thread;
    private Context context;
    private TextView textViewStatut;

    private List<Objet> objets;

    private static final int GLASS_SIZE = 40;

    private Bitmap glassImg;
    private Shader waterShader;
    private Bitmap waterImg;

    private int width;
    private int height;
    private int widthVerre;
    private int heightVerre;
    private ShapeDrawable shapeDrawable;
    private int deplacementX;

    public JeuView(Context activityContext, AttributeSet attrs) {
        super(activityContext, attrs);

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

        waterImg = BitmapFactory.decodeResource(getResources(), R.drawable.water);
        glassImg = BitmapFactory.decodeResource(getResources(), R.drawable.glass);
        waterShader = new BitmapShader(waterImg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        deplacementX = 0;

        objets = new ArrayList<Objet>();

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        redrawObjetcs(canvas);

        // Reset width and height
        this.width = this.getWidth();
        this.height = this.getHeight();

        // Draw water
        this.drawWaterTexture(canvas);

        // Draw glass
        this.drawGlass(canvas);

    }

    private void drawGlass(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Bitmap glassResized = getResizedBitmapWithSameRatio(glassImg, getWidthPxPerPercent()*GLASS_SIZE);

        int yVerre = height - glassResized.getHeight();
        int xVerre = (width/2)-(glassResized.getWidth()/2) + deplacementX;

        canvas.drawBitmap(glassResized, xVerre, yVerre, paint);
    }
    private void drawWaterTexture(Canvas canvas) {
        PathShape path = this.getPath();
        shapeDrawable = new ShapeDrawable(path);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setShader(this.waterShader);
        shapeDrawable.setBounds(0, 0, width, height);
        shapeDrawable.draw(canvas);
    }
    private int getXGlass() {
        return 0;
    }
    private PathShape getPath() {
        int xPercentOfDevice = getWidthPxPerPercent();
        Double widthVerreDouble = ((Integer)(xPercentOfDevice*(GLASS_SIZE-8))).doubleValue();
        this.widthVerre = widthVerreDouble.intValue();
        this.heightVerre = getWidthPxPerPercent()*19;

        int glassHeightPercent = getWidthPxPerPercent() * 18;
        int glassBottomY1 = getWidthPxPerPercent() * 20;


        int glassX1 = (width/2)-(widthVerre/2)+ deplacementX + xPercentOfDevice * 3,
                glassX2 = (width/2)+(widthVerre/2)+ deplacementX - xPercentOfDevice * 4,
                glassX3 = (width/2)+(widthVerre/2)+ deplacementX - xPercentOfDevice * 3,
                glassX4 = (width/2)-(widthVerre/2)+ deplacementX + xPercentOfDevice * 2,
                glassY1 = height - glassBottomY1,
                glassY2 = height - glassBottomY1,
                glassY3 = height- glassHeightPercent - glassBottomY1,
                glassY4 = height- glassHeightPercent - glassBottomY1;


        Log.d("ter.verreview", String.format("1[%d, %d] 2[%d, %d] 3[%d, %d] 4[%d, %d]", glassX1, glassY1, glassX2, glassY2, glassX3, glassY3, glassX4, glassY4));

        Path path = new Path();
        path.moveTo(glassX1, glassY1);
        path.lineTo(glassX2, glassY2);
        path.lineTo(glassX3, glassY3);
        path.lineTo(glassX4, glassY4);
        path.close();
        return new PathShape(path, width, height);
    }

    private int getWidthPxPerPercent() {
        return width / 100;
    }
    private int getHeightPxPerPercent() {
        return height / 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();
        switch (maskedAction) {
            case MotionEvent.ACTION_MOVE: // a pointer was moved
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    float x =  event.getX(i);
                    int middleWidth = width / 2;
                    int onePercentOfScreen = getWidthPxPerPercent();
                    if (event.getX(i) > middleWidth && deplacementX < 45*onePercentOfScreen) {
                        deplacementX += onePercentOfScreen;
                    }
                    if (event.getX(i) < middleWidth && deplacementX > -45*onePercentOfScreen) {
                        deplacementX -= onePercentOfScreen;
                    }
                    Log.i("ter.VerreView", "ACTION_MOVE");
                    break;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i("ter.VerreView", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i("ter.VerreView", "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("ter.VerreView", "ACTION_UP");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i("ter.VerreView", "ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i("ter.VerreView", "ACTION_CANCEL");
                break;
        }
        invalidate();
        return true;
    }


    // Utils
    private Bitmap getResizedBitmapWithSameRatio(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleWidth);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    private void redrawObjetcs(Canvas canvas) {
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
}