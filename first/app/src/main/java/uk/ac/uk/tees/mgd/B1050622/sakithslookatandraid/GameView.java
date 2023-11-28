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
    boolean playing = true;
    private boolean paused = false;
    long LastFrameTime=0;
    long Delay =100;
    Anim anim = new Anim();
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d("gameview", "create");
        anim.start();
    }

    private class Anim extends Thread{
        int counter = 0;
        int spriteIDs[]={
                R.drawable.demo,
                R.drawable.demo,
                R.drawable.demo
        };
        @Override
        public void run() {
            draw(spriteIDs[counter]);
        }

        private void draw(int sprite){
            SurfaceHolder holder = (SurfaceHolder) getHandler();
            Canvas canvas = holder.lockCanvas();
            if(canvas != null){
                canvas.drawColor(Color.WHITE);
                Paint paint = new Paint();
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),sprite);
                canvas.drawBitmap(bitmap,100,100,paint);
                holder.unlockCanvasAndPost(canvas);
            }

        }
        private void incromentCounter(){
            if (counter >= spriteIDs.length)
            {
                counter = 0;
            }
            counter++;
        }
    }

    @Override
    public void run() {
        Log.d("GameView", "run");
        while (playing) {
            long CurrentFrameTime = System.currentTimeMillis();
            if (CurrentFrameTime > LastFrameTime + Delay) {
                LastFrameTime = CurrentFrameTime;
                //incrimint all sprites at once
                anim.incromentCounter();
            }
        }
    }
    public void pause(){
        paused = !paused;
    }

    private void GameUpdate() {

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                playing = !playing;
                break;
        }
        return true;
    }
}