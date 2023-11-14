package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static String KEY = "MY KEY";
    EditText editText;
    Button button;
    Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        button = findViewById(R.id.pauseB);
        button.setOnClickListener(this);
        play = findViewById(R.id.button2);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("MainActivity", "Button pressed");
        int id = view.getId();
        if (id == R.id.pauseB){
            Intent intent = new Intent(this,DisplayAcitivity.class);
            String name = editText.getText().toString();
            if (name.length() == 0)
                return;
            intent.putExtra(KEY, name);
            startActivity(intent);
        } else if (id == R.id.button2) {
            Intent intent = new Intent(this, GameLayout.class);
            startActivity(intent);
        }
        Log.d("MainActivity", "after");
    }
}