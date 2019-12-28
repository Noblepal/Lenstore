package com.example.navdemo;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class App extends Application {
    static App instance;
    static public HashMap<String, ArrayList<Integer>> dataMap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataMap = new HashMap<>();

    }
}
