package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;

public class GameLayout extends AppCompatActivity implements View.OnClickListener  {

    Button button;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);
        Log.d("GameLayout", "o");
        gameView = findViewById(R.id.gameView);

//        gameView.run();

        Log.d("GameLayout", "onCreate");


        button = findViewById(R.id.pauseB);

        button.setOnClickListener(this);


//        while (playing) {
//            update();
//        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onClick(View view) {
        // terinate sidplay activity
        int id = view.getId();
        if (id == R.id.pauseB){
//            gameView.pause();
            finish();
        }
    }
}