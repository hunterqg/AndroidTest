package com.example.qingao;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by qingao on 15-11-12.
 */
public class MyTestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        ActiveAndroid.setLoggingEnabled(BuildConfig.DEBUG);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
