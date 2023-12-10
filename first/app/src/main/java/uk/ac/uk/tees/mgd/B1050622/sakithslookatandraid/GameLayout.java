package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;

public class GameLayout extends AppCompatActivity implements View.OnClickListener {

    Button button;
    GameView gameView;
    Dialog pause;
    Dialog settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        gameView = findViewById(R.id.gameView);
        pause = new Dialog(this);
        settings = new Dialog(this);

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
//
//    private void update()
//    {
//
//    }
    @Override
    public void onClick(View view) {
        // terinate sidplay activity
        int id = view.getId();
        if (id == R.id.pauseB){
            showPause();
            //gameView.pause();
            //finish();
        }
//        finish();
    }
    public void showPause()
    {
        pause.setContentView(R.layout.dialog_pause);
        pause.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button unpause;
        Button settings;
        Button exit;
        unpause = findViewById(R.id.bReturn);
        unpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.dismiss();
                gameView.unPause();
            }
        });
        pause.show();
    }
}