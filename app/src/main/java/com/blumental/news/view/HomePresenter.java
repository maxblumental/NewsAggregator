package com.blumental.news.view;

import com.blumental.news.core.MvpPresenter;

public interface HomePresenter extends MvpPresenter {

    void fetchNews();

    void nextPage();

    boolean canLoadMore();

    void onCategoryChange(String category);
}
