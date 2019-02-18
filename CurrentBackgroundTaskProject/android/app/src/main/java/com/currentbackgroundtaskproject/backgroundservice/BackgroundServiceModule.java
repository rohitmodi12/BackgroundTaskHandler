package com.currentbackgroundtaskproject.backgroundservice;

import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

//Work manager

public class BackgroundServiceModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext context;
    private WorkManager bgWorkManager;

    @Override
    public String getName() {
        /**
         * return the string name of the NativeModule which represents this class in JavaScript
         * In JS access this module through React.NativeModules.BackgroundService
         */
        return "BackgroundService";
    }

    @ReactMethod
    public void startBackgroundService(Callback cb) {

        bgWorkManager = WorkManager.getInstance();
        OneTimeWorkRequest bgWork = new OneTimeWorkRequest.Builder(BackgroundWorker.class).addTag("TAG_OUTPUT").build();
        bgWorkManager.enqueue(bgWork);
        cb.invoke(true);
    }

    @ReactMethod
    public void cancelBackgroundService(Callback cb) {
        WorkManager.getInstance().cancelAllWorkByTag("TAG_OUTPUT");
        cb.invoke(true);
    }

    public void toastMethod() {
        Log.d("startServiceCustom:", this.context.toString());
        WritableMap params = Arguments.createMap();
        params.putString("status", "timer");


        // sendEvent( "AudioBridgeEvent", params);
        Toast.makeText(this.context, "Alarm went off", Toast.LENGTH_SHORT).show();
    }

    public void sendEvent(String eventName, @Nullable WritableMap params) {
        Log.d("AudioBridgeEvent ", this.context.toString());
        Log.d("AudioBridgeEvent123 ", (this.context.hasActiveCatalystInstance() ? "ture" : "false"));
        // if (this.context.hasActiveCatalystInstance()) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
        // }

    }

    /* constructor */
    public BackgroundServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // if(this.context == null) {
        this.context = reactContext;
        // }

        Log.d("BackgroundServiceModule ", this.context.toString());
    }
}
