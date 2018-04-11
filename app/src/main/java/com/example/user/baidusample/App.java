package com.example.user.baidusample;


import android.app.Application;

import com.baidu.mapapi.SDKInitializer;


public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // Baidu maps
        SDKInitializer.initialize(this);
    }
}
