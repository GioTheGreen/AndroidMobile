package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private boolean paused = false;
    long LastFrameTime=0;
    long Delay =100;

    SurfaceHolder holder = getHolder();
    Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas;// = new Canvas(bitmap);// = holder.lockCanvas();

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("gameview", "create");
    }
    public void draw(Animation a){
        Log.d("gameview", "test");
        //canvas = holder.lockCanvas();           //cant lock but have to lock????
        Log.d("gameview", "test2");
        if(canvas != null){
            Log.d("gameview", "test3");
            canvas.drawColor(Color.WHITE);
            Log.d("gameview", "test4");
            Paint paint = new Paint();
            Log.d("gameview", "test5");
            Bitmap sprite = BitmapFactory.decodeResource(getContext().getResources(),a.spriteIDs[a.counter]);
            Log.d("gameview", "test6");
            canvas.drawBitmap(sprite,a.posx,a.posy,paint);
            Log.d("gameview", "test7");
            //holder.unlockCanvasAndPost(canvas);

            Log.d("gameview", "test8");
        }

    }
    @Override
    public void run() {
        Log.d("GameView", "run");

    }

    public void pause()
    {
        paused = !paused;
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