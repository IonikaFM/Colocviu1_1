package ro.pub.cs.systems.eim.Colocviu1_1;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;

import ro.pub.cs.systems.eim.Colocviu1_1.Constants;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private String instruction;

    public ProcessingThread(Context context, String instruction) {
        this.context = context;

        this.instruction = instruction;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        sleep();
        sendMessage();
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + instruction);
        Log.d("text" , intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
