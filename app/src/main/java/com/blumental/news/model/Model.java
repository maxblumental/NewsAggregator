package com.blumental.news.model;

import com.blumental.news.db.Article;

import java.util.List;

public interface Model {
    void getPage(int pageNumber, String category, UiCallback callback);

    void refresh(String category, UiCallback callback);

    interface UiCallback {
        void onLoaded(List<Article> news, boolean hasMore);

        void onError(String message);
    }
}
