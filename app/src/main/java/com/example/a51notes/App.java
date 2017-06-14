package com.example.a51notes;

import android.app.Application;

/**
 * Created by simonyan51 on 6/4/17.
 */

public class App extends Application {

    private static App instance;

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
