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
    private boolean playing = true;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

    }

    private class Anim extends Thread{
        int counter = 0;

        @Override
        public void run() {
            long LastFrameTime=0;
            long Delay =100;
            int spriteIDs[]={
                    //R.drawable.demoP
            };
        }
    }

    @Override
    public void run() {
    }

    private void GameUpdate() {

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