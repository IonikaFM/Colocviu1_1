package ro.pub.cs.systems.eim.Colocviu1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Colocviu1_1SecondaryActivity extends AppCompatActivity {
    TextView text;
    Button cancel, register;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.register) {
                setResult(RESULT_OK, null);
            } else {
                setResult(RESULT_CANCELED, null);
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_1_secondary);

        text = findViewById(R.id.textSecondary);
        cancel = findViewById(R.id.cancel);
        register = findViewById(R.id.register);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.TEXT)) {
            text.setText(intent.getStringExtra(Constants.TEXT));
        }

        cancel.setOnClickListener(buttonClickListener);
        register.setOnClickListener(buttonClickListener);
    }
}