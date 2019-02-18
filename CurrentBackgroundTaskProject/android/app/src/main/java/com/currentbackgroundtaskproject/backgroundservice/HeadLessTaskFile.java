package com.currentbackgroundtaskproject.backgroundservice;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class HeadLessTaskFile extends HeadlessJsTaskService {
    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            return new HeadlessJsTaskConfig(
                    "MyTaskService",
                    Arguments.fromBundle(extras),
                    2000,
                    true
            );
        }
        return null;
    }
}
