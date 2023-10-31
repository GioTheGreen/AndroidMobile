package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLayout extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        button = findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // terinate sidplay activity
        finish();
    }
}