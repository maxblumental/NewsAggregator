package com.blumental.news.di;

import com.blumental.news.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HomeModule.class, ModelModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}