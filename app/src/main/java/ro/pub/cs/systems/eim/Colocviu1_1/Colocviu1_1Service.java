package ro.pub.cs.systems.eim.Colocviu1_1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu1_1Service extends Service {
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String instruction = intent.getStringExtra(Constants.TEXT);
        processingThread = new ProcessingThread(this, instruction);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}