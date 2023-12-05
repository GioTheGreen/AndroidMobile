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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, SensorEventListener {
    private volatile boolean paused = false;
    long LastFrameTime=0;
    long Delay =100;
    private float bx,by,bz;
    SurfaceHolder holder;// = getHolder();
    Bitmap bitmap;// = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas;// = new Canvas(bitmap);// = holder.lockCanvas();

    private SensorManager manager;
    private Sensor gyroscope;
    //private final SensorEvent event;

    int piratid[]={
            R.drawable.demo,
            R.drawable.demo,
            R.drawable.demo
    };
    int woodid[]={
    };
    int coinid[]={
    };
    int bulletPid[]={
    };
    int bulletEid[]={
    };
    int Enimy1id[]={
    };
    int Enimy2id[]={
    };

    Animation animations[] =
            {
                    new Animation(piratid, 100, 2000)
            };
    private Thread thread;

    public GameView(Context context, AttributeSet attributeSet, SensorEvent event) {
        super(context, attributeSet);
        //this.event = event;
        Log.d("gameview", "create");
        holder = getHolder();

        manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


    }
    public void draw(Animation a){
        Log.d("gameview", "test2");
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            if (canvas != null) {
                Log.d("gameview", "test3");
                canvas.drawColor(Color.GREEN);
                Log.d("gameview", "test4");
                Paint paint = new Paint();
                Log.d("gameview", "test5");
                Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), a.spriteIDs[a.counter]);
                Log.d("gameview", "test6");
                canvas.drawBitmap(sprite, a.posx, a.posy, paint);
                Log.d("gameview", "test7");
                holder.unlockCanvasAndPost(canvas);

                Log.d("gameview", "test8");
            }
        }
    }
    @Override
    public void run() {
        Log.d("GameView", "run");
        while(!paused)
        {
            // Framerate
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
                Log.d("layout", "made");
                draw(a);
                Log.d("layout", "made2");
            }
            Tickupdate();
        }
        Log.d("GameView", "Stopping thread");
    }
    public void Tickupdate()
    {
        //animations[0].posx += by;
    }
    public void pause()
    {
        paused = true;
        manager.unregisterListener(this);
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
        manager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()== Sensor.TYPE_GYROSCOPE){
            bx=event.values[0];
            by=event.values[1];
            bz=event.values[2];

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    //@Override
    //public boolean onTouchEvent(MotionEvent event) {
    //    switch (event.getAction() & MotionEvent.ACTION_MASK) {
    //        case MotionEvent.ACTION_DOWN :
    //            playing = !playing;
    //            break;
    //    }
    //    return true;
    //}
}