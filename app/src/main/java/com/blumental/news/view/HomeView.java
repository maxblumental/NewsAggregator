package com.blumental.news.view;

import com.blumental.news.core.MvpView;

import java.util.List;

interface HomeView extends MvpView {

    void stopRefresh();

    void setNews(List<ArticleVM> items);

    void addNews(List<ArticleVM> items);

    void showError(String message);

    void startRefresh();
}
