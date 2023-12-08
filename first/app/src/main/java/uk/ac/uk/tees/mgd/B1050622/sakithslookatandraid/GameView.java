package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Vector;

import kotlin.collections.FloatIterator;

public class GameView extends SurfaceView implements Runnable, SensorEventListener {
    private volatile boolean paused = false;
    private boolean playing = false;
    private long LastFrameTime = 0;
    private final long Delay =100;
    private float dir = 0;//for motion controls
    private final float acceleration = 3;
    private float velocity = -80;
    private final float maxXSpeed= 5;
    private final float maxYSpeed= 80;
    private final SurfaceHolder holder;// = getHolder();
    private Bitmap bitmap;// = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
    private Canvas canvas;// = new Canvas(bitmap);// = holder.lockCanvas();
    private int offset= 0;
    private final SensorManager manager;
    private final Sensor sensor;

    private final int[] piratid ={
            R.drawable.demo,
            R.drawable.demo,
            R.drawable.demo
    };

    //private Animation[] animations =
    //        {
    //                new Animation(piratid, -75, 1000)
    //        };
    private Vector<Animation> animations = new Vector<Animation>(0);
    private Thread thread;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("gameview", "create");
        holder = getHolder();

        manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(GameView.this,sensor,manager.SENSOR_DELAY_GAME);

        animations.addElement( new Animation(piratid, 400, 1000));
    }
    public void draw(Animation a){
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            if (canvas != null) {
                canvas.drawColor(Color.GREEN);
                Paint paint = new Paint();
                Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), a.getCurrent());
                canvas.drawBitmap(sprite, a.getPosx(), a.getPosy(), paint);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    @Override
    public void run() {
        Log.d("GameView", "run");
        while(!paused)
        {
            // Framerate for animations
            long CurrentFrameTime = System.currentTimeMillis();
            if (CurrentFrameTime > LastFrameTime + Delay) {
                LastFrameTime = CurrentFrameTime;

                for (Animation a: animations)
                {
                    a.next();
                }
            }
            for (Animation a: animations)
            {
                draw(a);
            }
            //update game loop
            if (playing)
            {
                if (velocity + acceleration > maxYSpeed) {
                    velocity = maxYSpeed;
                } else if (velocity + acceleration < -maxYSpeed) {
                    velocity = -maxYSpeed;
                } else {
                    velocity += acceleration;
                }
                float nextY = animations.firstElement().getPosy() + velocity;
                float nextX = animations.firstElement().getPosx() + dir;
                animations.firstElement().setPos((int) nextX, (int) nextY);
                if (animations.firstElement().getPosx() > getWidth()) {
                    animations.firstElement().setPos(-75, animations.firstElement().getPosy());
                } else if (animations.firstElement().getPosx() < -75) {
                    animations.firstElement().setPos(getWidth(), animations.firstElement().getPosy());
                }
                dir = 0;// very needed
            }
        }
        Log.d("GameView", "Stopping thread");
    }

    public void pause()
    {
        paused = true;
        try {
            Log.d("GameView", "pause");
            thread.join();
            Log.d("GameView", "thread finished running");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void resume() {
        paused = false;
        thread = new Thread(this);
        thread.start();
        //animations.firstElement().setPos(100, 100);
        Log.d("GameView", animations.capacity() + "");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Math.abs(event.values[1]) > 0.3) //not 0.1, 0.2, 0.5, 0.4, 0.25
        {
            dir =-10 * Math.max(Math.min((event.values[1] - 0.3f), maxXSpeed),-maxXSpeed); // 5 seems like a good max speed
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                if (!playing)
                {
                    playing = true;
                }
                else
                {
                    velocity = -80;
                }
                Log.d("GameView","x: "+animations.firstElement().getPosx() + " y: " +animations.firstElement().getPosy());
                break;
        }
        return true;
    }
}