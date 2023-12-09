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

import java.util.Random;
import java.util.Vector;


public class GameView extends SurfaceView implements Runnable, SensorEventListener {
    private volatile boolean paused = false;
    private boolean playing = false;
    private long LastFrameTime = 0;
    private final long Delay =100;
    private float lastSpwanX = 0;
    private float lastSpwanY = 0;
    private boolean lastSpwanType = false;
    private int maxSpawnCap = 1;
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
    private final int[] platform ={
            R.drawable.platform1
    };
    private final int[] enemy1 ={
            R.drawable.platform1
    };
    private final int[] enemy2 ={
            R.drawable.platform1
    };
    private Vector<Animation> animations = new Vector<Animation>(0);
    private Thread thread;
    private void addAnimation(int[] spriteSet,int x, int y)
    {
        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), spriteSet[0]);
        animations.addElement( new Animation(spriteSet, x, y, sprite.getWidth(),sprite.getHeight(), false));
        lastSpwanX = x;
        lastSpwanY = y;
    }
    private void addEnemyAnimation(boolean type, int x, int y )
    {
        int[] spriteSet = enemy1;
        if (type)
        {
            spriteSet = enemy2;
        }
        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), spriteSet[0]);
        animations.addElement( new Animation(spriteSet, x, y, sprite.getWidth(),sprite.getHeight(), true));
        lastSpwanX = x;
        lastSpwanY = y;
    }
    private void removeAnimation()
    {
        if (animations.capacity() > 1)
        {
            animations.remove(1);
        }
    }
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("gameview", "create");
        holder = getHolder();

        manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(GameView.this,sensor,SensorManager.SENSOR_DELAY_FASTEST);//"sensor_delay_game" was too slow, player was lagging

        addAnimation(piratid, 863, 1900);
        addAnimation(platform,825,2200);
        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), platform[0]);
        int step = sprite.getHeight()/2;
        int stepsToFitScreen = getHeight()/step;
        maxSpawnCap = (stepsToFitScreen / 3);
        //Random random = new Random();
        for (int i = 0; i < 5; i++)
        {
            int nextspace =9;// random.nextInt(13) + 3;
            addAnimation(platform,(int)lastSpwanX,(int)lastSpwanY - (nextspace * step));
        }
    }
    public void draw(Vector<Animation> animations){
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            if (canvas != null) {
                canvas.drawColor(Color.GREEN);
                Paint paint = new Paint();
                for (Animation a: animations)
                {
                    if (a.getAlive())
                    {
                        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), a.getCurrent());
                        canvas.drawBitmap(sprite, a.getPosx(), a.getPosy() + offset, paint);
                    }
                }

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
                if (nextX > getWidth()) {
                    nextX=-75;
                } else if (nextX < -75) {
                    nextX= getWidth();
                }
                // ckeck land colltion
                for (int i = 1; i < animations.capacity()-1; i++) //start from 1 to skip player
                {
                    if (animations.firstElement().doesLand(animations.get(i), nextX, nextY)) {
                        velocity = -80;
                        break;
                    }
                }
                dir = 0;// reset motion controls
                if ((animations.firstElement().getPosy() + offset < (getHeight()/4)) && velocity < 0)// offset calc
                {
                    offset -= velocity;//not sure if this will create problem or not
                }
            }
            draw(animations);
        }
        Log.d("GameView", "Stopping thread");
    }
    public void gameOver()
    {
        //nothing yet
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
        if (Math.abs(event.values[1]) > 0.3f) //not 0.1, 0.2, 0.5, 0.4, 0.25...  0.3 is right
        {
            dir =-10 * Math.max(Math.min((event.values[1]), maxXSpeed),-maxXSpeed); // 5 seems like a good max speed
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
                    //velocity = -80;
                    Log.d("GameView", animations.capacity() + "");
                }
                Log.d("GameView","x: "+animations.firstElement().getPosx() + " y: " +animations.firstElement().getPosy());
                break;
        }
        return true;
    }
}