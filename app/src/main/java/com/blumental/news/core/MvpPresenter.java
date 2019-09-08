package com.blumental.news.core;

public interface MvpPresenter {

    void attach(MvpView view);

    void detach();
}
