package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameLayout extends AppCompatActivity implements View.OnClickListener {

    Button button;
    GameView gameView;
    Dialog pause;
    Dialog settings;
    TextView textView;
    ImageView imageView;
    void setScore(int i){
        textView.setText(""+i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        gameView = findViewById(R.id.gameView);
        pause = new Dialog(this);

//        gameView.run();

        Log.d("GameLayout", "onCreate");

        textView = findViewById(R.id.txScore2);
        button = findViewById(R.id.pauseB);
        imageView = findViewById(R.id.imageViewCannon);
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
                Intent rintent = getIntent();
                rintent.putExtra("Score",0+ "");
                rintent.putExtra("Status", false);
                setResult(RESULT_OK, rintent);
                finish();
            }
        });
        pause.show();
    }
    public void showGameOver()
    {
        Intent rintent = getIntent();
        rintent.putExtra("Score",gameView.score + "");
        rintent.putExtra("Status", true);
        setResult(RESULT_OK, rintent);
        finish();
    }
    public void cannonReady(){
        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.can_1, null);
        imageView.setImageDrawable(drawable);
    }
    public void cannonNotReady(){
        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.can_2, null);
        imageView.setImageDrawable(drawable);
    }
}