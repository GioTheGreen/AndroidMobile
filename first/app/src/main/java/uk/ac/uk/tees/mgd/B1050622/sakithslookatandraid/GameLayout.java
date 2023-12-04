package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;

public class GameLayout extends AppCompatActivity implements View.OnClickListener {

    Button button;
    GameView gameView;
    int piratid[]={
        R.drawable.demo,
        R.drawable.demo,
        R.drawable.demo
    };
    boolean playing = true;
    long LastFrameTime=0;
    long Delay =100;
    Animation animations[] =
            {
                    new Animation(piratid, 100, 100)
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        gameView = findViewById(R.id.gameView);

        //gameView.run();


        button = findViewById(R.id.pauseB);

        button.setOnClickListener(this);



        while (playing) {
            update();
        }
    }
    private void update()
    {
        long CurrentFrameTime = System.currentTimeMillis();
        if (CurrentFrameTime > LastFrameTime + Delay) {
            LastFrameTime = CurrentFrameTime;

            for (Animation a: animations)
            {
                a.next();
            }
        }
        for (Animation a: animations)
        {
            Log.d("layout", "made");
            gameView.draw(a);
            Log.d("layout", "made2");
        }
    }
    @Override
    public void onClick(View view) {
        // terinate sidplay activity
        int id = view.getId();
        if (id == R.id.pauseB){
            gameView.pause();
        }
        finish();
    }
}