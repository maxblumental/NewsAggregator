package com.blumental.news;

import android.app.Application;
import android.os.Handler;

import com.blumental.news.di.AppComponent;
import com.blumental.news.di.DaggerAppComponent;
import com.blumental.news.di.ModelModule;

public class NewsAggregatorApp extends Application {

    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Handler handler = new Handler(getMainLooper());
        component = DaggerAppComponent.builder()
                .modelModule(new ModelModule(getApplicationContext(), handler))
                .build();
    }
}
