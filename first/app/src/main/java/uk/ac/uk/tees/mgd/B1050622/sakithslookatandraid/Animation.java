package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

public class Animation {
    public int counter = 0;
    public int posx = 100;
    public int posy = 100;
    public int spriteIDs[]={
            R.drawable.demo,
            R.drawable.demo,
            R.drawable.demo
    };
    public Animation(int a[], int x, int y)
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
}
