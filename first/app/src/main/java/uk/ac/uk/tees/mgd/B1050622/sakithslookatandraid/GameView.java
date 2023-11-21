package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    //private Thread gameThread;
    private SurfaceHolder surfaceHolder;
    private volatile boolean playing;
    private Canvas canvas;
    private Bitmap bitmap;
    private boolean isMoving;
    //private float velocity = 250; // 250 px/s
    //private float xPos = 10, yPos = 10;
    //private int frameW = 115, frameH = 137;
    //private int frameCount = 8;
    //private int currentFrame = 0;
    private long fps;
    private long timeThisFrame;
    private long lastFrameChangeTime = 0;
    //private int frameLengthInMS = 100;
    //private Rect frameToDraw = new Rect(0,0,frameW,frameH);
    //private RectF whereToDraw = new RectF(xPos, yPos, xPos + frameW, frameH);

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        surfaceHolder = getHolder();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.run);
        //bitmap = Bitmap.createScaledBitmap(bitmap, frameW * frameCount, frameH, false);
    }


    @Override
    public void run() {
        //while (playing)
        //{
        draw();
        //}
    }

    private void update() {

    }



    public void draw() {
        if (surfaceHolder.getSurface().isValid())
        {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //public void pause()
    //{
       // playing = false;
        //try {
            //gameThread.join();
        //} catch (InterruptedException e) {
            //Log.e("GameView", "Interrupted");
        //}
    //}

    //public void resume()
    //{
       // playing = true;
        //gameThread = new Thread(this);
        //gameThread.start();
    //}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                isMoving = !isMoving;
                Log.d("GameLayout", "" + isMoving);
                break;
        }
        return true;
    }
}