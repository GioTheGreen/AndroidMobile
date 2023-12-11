package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity implements View.OnClickListener
{
    Button TryAgain;
    Button mainMenu;
    Button quit;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gameover);

        TryAgain = findViewById(R.id.bTryAgain);
        mainMenu = findViewById(R.id.bMainMenu);
        quit = findViewById(R.id.bQuitGame);
        textView = findViewById(R.id.txScoreForDeath);

        TryAgain.setOnClickListener(this);
        mainMenu.setOnClickListener(this);
        quit.setOnClickListener(this);
        String name = getIntent().getStringExtra("Score");
        textView.setText(name);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bTryAgain){
            finish();
        }
        else if(id == R.id.bMainMenu){
            finish();
        }
        else if(id == R.id.bQuitGame){
            finish();
        }
    }
}
