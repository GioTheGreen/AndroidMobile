package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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
    Dialog gameOver;
    TextView textView;
    void setScore(int i){
        textView.setText(""+i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        gameView = findViewById(R.id.gameView);
        pause = new Dialog(this);
        gameOver = new Dialog(this);

//        gameView.run();

        Log.d("GameLayout", "onCreate");

        textView = findViewById(R.id.txScore2);
        button = findViewById(R.id.pauseB);

        button.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume(this);
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
            gameView.pause();
            //finish();
        }
//        finish();
    }
    public void showPause()
    {
        pause.setContentView(R.layout.dialog_pause);
        pause.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pause.setCanceledOnTouchOutside(false);
        pause.setCancelable(false);
        Button unpause;
        Button settings;
        unpause = pause.findViewById(R.id.bReturn);
        unpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.dismiss();
                gameView.unPause();
            }
        });
        Button exit;
        exit = pause.findViewById(R.id.bExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.dismiss();
                gameView.exitGame();
                //finish();
            }
        });
        pause.show();
    }
    public void showGameOver()
    {
//        Intent intent = new Intent(GameOverActivity.class, this);
//        startActivity(intent);

        gameOver.setContentView(R.layout.dialog_gameover);
        gameOver.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        gameOver.setCanceledOnTouchOutside(false);
        gameOver.setCancelable(false);
        Button TryAgain;
        TryAgain = gameOver.findViewById(R.id.bTryAgain);
        TryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.exitGame();
                gameView.resume(GameLayout.this);
            }
        });
        Button mainMenu;
        mainMenu = gameOver.findViewById(R.id.bMainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.exitGame();
            }
        });
        Button quit;
        quit = gameOver.findViewById(R.id.bQuitGame);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOver.dismiss();
                System.exit(0);
            }
        });
        gameOver.show();
        gameView.pause();
    }
}