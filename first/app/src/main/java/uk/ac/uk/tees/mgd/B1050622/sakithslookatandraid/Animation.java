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
    private long lastFireTime = 0;
    private final long delay = 2000;
    private boolean alive = true;
    private int e = 0;
    private int posx = 0;
    private int posy = 0;
    private int preposx = 0;
    private int preposy = 0;
    private int sizeX = 1;
    private int sizeY = 1;
    private int[] spriteIDs={
            R.drawable.demo
    };
    public Animation(int[] a, int x, int y, int sx, int sy, int enemy)
    {
        spriteIDs = a;
        posx = x;
        posy = y;
        sizeX = sx;
        sizeY = sy;
        e = enemy;
        if (e == 2)
        {
            lastFireTime = System.currentTimeMillis();
        }
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
    public boolean fire()
    {
        long currrentTime = System.currentTimeMillis();
        if (currrentTime > lastFireTime + delay)
        {
            lastFireTime = currrentTime;
            return true;
        }
        return false;
    }
    public void setPos(int x, int y)
    {
        preposx = getPosx();
        preposy = getPosy();
        posx = x;
        posy = y;
    }
    public int getPosx(){return posx;}
    public int getPosy(){return posy;}
    public int getPrePosy(){return preposy;}
    public int getPrePosx(){return preposx;}
    public boolean getAlive(){return alive;}
    public void kill(){alive = false;}
    public int isEnemy(){return e;}
    public boolean doesLand(Animation other, float nextX, float nextY)  //always player calling, other can be platform or enemy
    {
        if ((other.alive) && ((nextX >= other.posx && nextX <= other.posx + other.sizeX) || (nextX +sizeX <= other.posx+ other.sizeX && nextX +sizeX >= other.posx)) && (posy+sizeY < other.posy) && (nextY + sizeY >= other.posy))
        {
            setPos((int)nextX,other.posy - sizeY);
            if (other.isEnemy() > 0)
            {
                other.kill();
            }
            return true;
        }
        else
        {
            //setPos((int)nextX,(int)nextY);  <- this, this line right here has coursed me nothing but grief
            return false;
        }
    }
    public boolean hit(Animation other)//enemy calls on player, player calls on coins, bullets call on everything
    {
        if ((other.posx > posx && other.posx < posx+sizeX && other.posy > posy && other.posy < posy+sizeY)||(other.posx + other.posx > posx && other.posx + other.sizeX < posx+sizeX && other.posy > posy && other.posy < posy+sizeY)||(other.posx > posx && other.posx < posx+sizeX && other.posy + other.sizeY > posy && other.posy+ other.posy < posy+sizeY)||(other.posx + other.sizeX > posx && other.posx + other.posx < posx+sizeX && other.posy + other.sizeY > posy && other.posy + other.sizeY < posy+sizeY))
        {
            if (other.isEnemy() != e)
            {
                other.kill();
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
