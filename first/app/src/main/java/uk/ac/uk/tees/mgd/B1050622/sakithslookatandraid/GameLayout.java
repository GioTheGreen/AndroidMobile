package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameLayout extends AppCompatActivity implements View.OnClickListener {

    Button button;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        gameView = findViewById(R.id.gameView);
        gameView.run();
        button = findViewById(R.id.pauseB);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // terinate sidplay activity
        finish();
    }
}