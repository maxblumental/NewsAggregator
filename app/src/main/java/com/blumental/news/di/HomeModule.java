package com.blumental.news.di;

import com.blumental.news.view.HomePresenter;
import com.blumental.news.view.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
class HomeModule {

    @Provides
    HomePresenter provideHomePresenter(MainPresenter presenter) {
        return presenter;
    }
}
