package com.currentbackgroundtaskproject.backgroundservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactApplicationContext;

import java.util.Timer;
import java.util.concurrent.CountDownLatch;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundWorker extends Worker {
    private Context myContext;
    public BackgroundServiceModule moduleThis;
    // public BackgroundServiceModule workerContext;
    private ReactApplicationContext reactContext;
    private ReactNativeHost mReactNativeHost;
    Timer timer;

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        myContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        timer = new Timer();
        long delay = 5000;  //long: delay in milliseconds before task is to be executed.
        long period = 5000; //long: time in milliseconds between successive task executions.

        timer.scheduleAtFixedRate(
                new java.util.TimerTask() {

                    @SuppressLint("LongLogTag")
                    public void run() {
                        Log.d("startServiceCustom BackgroundWorker:", "rohit bg");
                        Intent serviceIntent = new Intent(myContext, HeadLessTaskFile.class);
                        serviceIntent.putExtra("hasInternet", "called");
                        myContext.startService(serviceIntent);
                    }
                },
                delay, period);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @Override
    public void onStopped() {
        timer.cancel();
        super.onStopped();
    }
}
