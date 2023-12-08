package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.core.content.res.ResourcesCompat;

public class Animation {
    private int counter = 0;
    private boolean alive = true;
    private boolean e = false;
    private int posx = 0;
    private int posy = 0;
    private int preposx = 0;
    private int preposy = 0;
    private int sizeX = 1;
    private int sizeY = 1;
    private int[] spriteIDs={
            R.drawable.demo
    };
    public Animation(int[] a, int x, int y, int sx, int sy, boolean enemy)
    {
        spriteIDs = a;
        posx = x;
        posy = y;
        sizeX = sx;
        sizeY = sy;
    }
    public int getCurrent()
    {
        return spriteIDs[counter];
    }

    public void next()
    {
        counter++;
        if (counter >= spriteIDs.length) {
            counter = 0;
        }
    };
    public void setPos(int x, int y)
    {
        preposx = getPosx();
        preposy = getPosy();
        posx = x;
        posy = y;
    }
    public int getPosx(){return posx;}
    public int getPosy(){return posy;}
    public void kill(){alive = false;}
    public boolean isEnemy(){return e;}
    public boolean doesLand(Animation other, float nextX, float nextY)  //always player calling, other can be platform or enemy
    {
        if ((other.alive) && ((nextX >= other.posx && nextX <= other.posx + other.sizeX) || (nextX +sizeX <= other.posx+ other.sizeX && nextX +sizeX >= other.posx)) && (posy+sizeY < other.posy) && (nextY + sizeY >= other.posy))
        {
            posy = other.posy - sizeY;
            return true;
        }
        return false;
    }
}
