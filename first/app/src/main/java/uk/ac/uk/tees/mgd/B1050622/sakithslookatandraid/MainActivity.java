package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static String KEY = "MY KEY";
    EditText editText;
    Button button;
    Button play;
    TextView display;
    int HS = 0;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityresult) {
                    int result = activityresult.getResultCode();
                    Intent data = activityresult.getData();
                    if (Integer.valueOf(data.getStringExtra("Score")) > HS) {
                        HS = Integer.valueOf(data.getStringExtra("Score"));
                        display.setText("High Score: " + data.getStringExtra("Score"));
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.mainTextDis);
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
            activityResultLauncher.launch(intent);
        }
        Log.d("MainActivity", "after");
    }
    public void showOver(){
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("Score" ,"gameView.score");
        startActivity(intent);
    }
}