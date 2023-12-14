package uk.ac.uk.tees.mgd.B1050622.sakithslookatandraid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayAcitivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button2);

        String name = getIntent().getStringExtra(MainActivity.KEY);
        textView.setText(name);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // terinate sidplay activity
        finish();
    }
}