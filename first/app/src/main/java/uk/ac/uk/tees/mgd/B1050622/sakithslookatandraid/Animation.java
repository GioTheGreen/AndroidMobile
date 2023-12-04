package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

public class Animation {
    int counter = 0;
    int posx = 100;
    int posy = 100;
    int spriteIDs[]={
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
        if (counter >= spriteIDs.length)
        {
            counter = 0;
        }
        counter++;
    };
}
