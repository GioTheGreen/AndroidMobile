package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private boolean playing = true;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new Anim().start();
    }

    private class Anim extends Thread{
        int counter = 0;

        @Override
        public void run() {
            long LastFrameTime=0;
            long Delay =100;
            int spriteIDs[]={
                    R.drawable.demo
            };

            while(true){
                if (playing){
                    long CurrentFrameTime = System.currentTimeMillis();
                    if (CurrentFrameTime > LastFrameTime + Delay)
                    {
                        if (counter >= spriteIDs.length)
                        {
                            counter = 0;
                        }
                        draw(spriteIDs[counter]);
                        LastFrameTime = CurrentFrameTime;
                        counter++;
                    }
                }
            }
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

        };


    }

    @Override
    public void run() {
        return;
    }

    private void GameUpdate() {
        return;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //switch (event.getAction() & MotionEvent.ACTION_MASK) {
        //    case MotionEvent.ACTION_DOWN :
        //        isMoving = !isMoving;
        //        Log.d("GameLayout", "" + isMoving);
        //        break;
        //}
        return true;
    }
}