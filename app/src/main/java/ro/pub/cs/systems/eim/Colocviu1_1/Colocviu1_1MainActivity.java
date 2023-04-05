package ro.pub.cs.systems.eim.Colocviu1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_1MainActivity extends AppCompatActivity {
    Button north, south, west, east, navigate;
    TextView etiquette;
    int noClicks;
    private int serviceStatus = Constants.SERVICE_STOPPED;
    private IntentFilter intentFilter = new IntentFilter();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ceva", "ceva");
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    public class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            StringBuilder text = new StringBuilder(etiquette.getText().toString());
            boolean textWasNull = text.length() == 0 ? true : false;

            if(view.getId() == R.id.north) {
                if(!textWasNull) {
                    text.append(", ");
                }
                text.append("North");
                etiquette.setText(text.toString());
                noClicks++;
            } else if(view.getId() == R.id.east) {
                if(!textWasNull) {
                    text.append(", ");
                }
                text.append("East");
                etiquette.setText(text.toString());
                noClicks++;
            } else if(view.getId() == R.id.west) {
                if(!textWasNull) {
                    text.append(", ");
                }
                text.append("West");
                etiquette.setText(text.toString());
                noClicks++;
            } else if(view.getId() == R.id.south) {
                if(!textWasNull) {
                    text.append(", ");
                }
                text.append("South");
                etiquette.setText(text.toString());
                noClicks++;
            } else if(view.getId() == R.id.navigate) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_1SecondaryActivity.class);
                intent.putExtra(Constants.TEXT, etiquette.getText().toString());
                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                etiquette.setText("");
                noClicks = 0;
            }

            if (noClicks >= 4 && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_1Service.class);
                intent.putExtra(Constants.TEXT, etiquette.getText().toString());
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    ClickListener clickListener = new ClickListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_1_main);

        noClicks = 0;

        etiquette = findViewById(R.id.etiquette);

        north = findViewById(R.id.north);
        east = findViewById(R.id.east);
        west = findViewById(R.id.west);
        south = findViewById(R.id.south);
        navigate = findViewById(R.id.navigate);

        north.setOnClickListener(clickListener);
        east.setOnClickListener(clickListener);
        west.setOnClickListener(clickListener);
        south.setOnClickListener(clickListener);
        navigate.setOnClickListener(clickListener);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else {
            noClicks = 0;
            etiquette.setText("");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(Constants.NO_CLICKS, String.valueOf(noClicks));
        savedInstanceState.putString(Constants.TEXT, etiquette.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.NO_CLICKS)) {
            noClicks = Integer.parseInt(savedInstanceState.getString(Constants.NO_CLICKS));
        } else {
            noClicks = 0;
        }
        if (savedInstanceState.containsKey(Constants.TEXT)) {
            etiquette.setText(savedInstanceState.getString(Constants.TEXT));
        } else {
            etiquette.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_1Service.class);
        stopService(intent);
        super.onDestroy();
    }
}