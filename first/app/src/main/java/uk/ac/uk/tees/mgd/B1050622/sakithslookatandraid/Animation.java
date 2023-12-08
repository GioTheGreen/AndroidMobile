package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

public class Animation {
    private int counter = 0;
    private int posx = 0;
    private int posy = 0;

    private int preposx = 0;
    private int preposy = 0;
    private int[] spriteIDs={
            R.drawable.demo
    };
    public Animation(int[] a, int x, int y)
    {
        spriteIDs = a;
        posx = x;
        posy = y;
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
}
