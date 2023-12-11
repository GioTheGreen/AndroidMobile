package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import static android.content.Context.SENSOR_SERVICE;

import android.app.Dialog;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;


public class GameView extends SurfaceView implements Runnable, SensorEventListener {
    private volatile boolean paused = false;
    private boolean playing = false;
    public int score = 0;
    private boolean fiestLoop = true;
    private long LastFrameTime = 0;
    private final long Delay =250;
    private float lastSpwanX = 0;
    private float lastSpwanY = 0;
    private boolean lastSpwanType = false;
    private int step = 0;
    private int maxSpawnCap = 20;
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
            R.drawable.demo_e1
    };
    private final int[] enemy2 ={
            R.drawable.demo_e2
    };
    private final int[] eBullet ={
            R.drawable.demo_e_bullet
    };
    private final int[] pBullet ={
            R.drawable.demo_p_bullet
    };
    private final int[] coin ={
            R.drawable.demo_coin
    };
    private Vector<Animation> animations = new Vector<Animation>(0);
    private Vector<Animation> bullets = new Vector<Animation>(0);
    private Vector<Animation> coins = new Vector<Animation>(0);
    private Thread thread;
    private GameLayout gameLayout;

    private void addAnimation(int w)
    {

        Random r = new Random();
        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), platform[0]);
        int bound = 21;
        if (lastSpwanType)
        {
            bound = 7;
        }
        int spwanX = r.nextInt( w- sprite.getWidth());
        int spwanY = (int)lastSpwanY - ((r.nextInt(bound) + 3)* step);//23 is the max player can go reliability, 3 so platforms aren't connected
        animations.addElement( new Animation(platform, spwanX, spwanY, sprite.getWidth(),sprite.getHeight(), 0));
        lastSpwanType = false;
        lastSpwanX = spwanX;
        lastSpwanY = spwanY;
    }
    private void addEnemyAnimation(int type, int w)
    {
        Random r = new Random();
        int[] spriteSet = enemy1;
        if (type > 1)
        {
            spriteSet = enemy2;
        }
        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), spriteSet[0]);
        Bitmap platformsprite = BitmapFactory.decodeResource(getContext().getResources(), platform[0]);
        int spwanX;
        if (lastSpwanX+ platformsprite.getWidth() > w - sprite.getWidth())//don't want spawning right on top of platforms
        {
            spwanX = r.nextInt((int)lastSpwanX - sprite.getWidth());
        }
        else
        {
            spwanX = r.nextInt((int) ((int)lastSpwanX-sprite.getWidth() + (w - sprite.getWidth() - (lastSpwanX + platformsprite.getWidth()))));
            if (spwanX >lastSpwanX - sprite.getWidth())
            {
                spwanX += platformsprite.getWidth() + sprite.getWidth();
            }
        }
        int spwanY = (int)lastSpwanY - ((r.nextInt(10) + 5)* step);
        animations.addElement( new Animation(spriteSet, spwanX, spwanY, sprite.getWidth(),sprite.getHeight(), type));
        lastSpwanType = true;
        lastSpwanX = spwanX;
        lastSpwanY = spwanY;
    }
    private void removeAndAddAnimation(int w)
    {
        animations.remove(1);
        if (lastSpwanType) // last = enemy
        {
            addAnimation(w);
        }
        else // last = platform
        {
            Random r = new Random();
            int rand = r.nextInt(100);
            if ( rand > (20 + Math.min((offset/25000),40))) //progressively difficult depending on height not score
            {
                addAnimation(w);
            }
            else if (rand > (5 + Math.min((offset/200000),10)))
            {
                addEnemyAnimation(1, w);
            }
            else
            {
                addEnemyAnimation(2, w);
            }
        }
    }
    public void addPlayerBullet(int type,int x, int y)
    {
        int[] spriteSet = pBullet;
        if (type > 0)
        {
            spriteSet = eBullet;
        }
        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), spriteSet[0]);
        bullets.addElement(new Animation(pBullet,x,y,sprite.getWidth(),sprite.getHeight(), type));
    }
    public void removeBullets(Vector<Integer> bToRemove)
    {
        int removed = 0;
        for (int a: bToRemove)  // just in case two or more bullets needs to be removed in the same frame
        {
            bullets.remove(a - removed);
            removed++;  //only works if values passed in ascending order.
        }
    }
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("gameview", "create");
        holder = getHolder();


        manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(GameView.this,sensor,SensorManager.SENSOR_DELAY_FASTEST);//"sensor_delay_game" was too slow, player was lagging

        Bitmap player = BitmapFactory.decodeResource(getContext().getResources(), piratid[0]); //manually add player
        animations.addElement( new Animation(piratid, 863, 1800, player.getWidth(),player.getHeight(), 0)); //<<<<<<<<<<<<<<<<<< problem, how to set place since cant get height

        Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(), platform[0]); //manually add first platform to be under player
        animations.addElement( new Animation(platform, 800, 2000, sprite.getWidth(),sprite.getHeight(), 0));
        lastSpwanType = false;
        lastSpwanX = 800;
        lastSpwanY = 2000;

        step = sprite.getHeight()/2;
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
                gameLayout.setScore(score);
            }
            //update game loop
            if (playing)
            {
                if (fiestLoop)// have to be here as during construction and first loop, cant use getWeidth() function
                {
                    for (int i = 0; i < maxSpawnCap; i++)
                    {
                        addAnimation(getWidth());
                    }
                    score = 0;
                    offset = 0;
                    fiestLoop = false;
                }
                //player update
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
                // enemy 2 update (spawn enemy bullet)

                // bullet update

                // ckeck land colltion (player vs platform & enemy)
                boolean land = false;
                for (int i = 1; i < animations.size(); i++) //start from 1 to skip player
                {
                    boolean isEnemy = animations.elementAt(i).isEnemy() != 0;
                    if (animations.firstElement().doesLand(animations.elementAt(i), nextX, nextY)) {
                        velocity = -80;
                        land = true;
                        if (isEnemy)
                        {
                            score += 7500;
                        }
                    }
                }
                if (!land)
                {
                    animations.firstElement().setPos((int)nextX,(int)nextY);
                }
                //player fall?
                if ((animations.firstElement().getPosy() + offset > getHeight()) || !animations.firstElement().getAlive())
                {
                    gameOver();//have to check after poss update
                }
                // check hit colltion( enemy vs player) enemy has to call function

                // check coin colliion(player vs coin)

                //check bullet colltion(player & enemy vs bullet ) kill self if hit

                //remove and add platforms
                for (int i = 1; i < animations.size(); i++) //start from 1 to skip player
                {
                    if (animations.elementAt(i).getPosy() + offset > getHeight() + 10)
                    {
                        removeAndAddAnimation(getWidth());
                    }
                }
                //remove bullets
                Vector<Integer> bulletIndex = new Vector<>(0);
                for (int i = 0; i < bullets.size(); i++)
                {
                    //check if out of bounds
                    if (!bullets.elementAt(i).getAlive())
                    {
                        bulletIndex.addElement(i);
                    }
                }
                removeBullets(bulletIndex);
                dir = 0;// reset motion controls
                if ((animations.firstElement().getPosy() + offset < (getHeight()/3)) && velocity < 0)// offset calc
                {
                    offset -= velocity;//not sure if this will create problem or not
                    score -= velocity;
                }
            }
            draw(animations);
        }
        Log.d("GameView", "Stopping thread");
    }
    public void gameOver()
    {
        gameLayout.showGameOver();
    }
    public void pause()
    {
        playing = false;
    }
    public void unPause()
    {
        playing = true;
    }
    public void exitGame()
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
    public void resume(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        paused = false;
        thread = new Thread(this);
        thread.start();
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
                    //playing = !playing;
                    //spawn bullet
                }
                Log.d("GameView","x: "+animations.firstElement().getPosx() + " y: " +animations.firstElement().getPosy());
                break;
        }
        return true;
    }
}