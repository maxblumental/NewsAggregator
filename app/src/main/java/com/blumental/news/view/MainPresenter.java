package com.blumental.news.view;

import com.blumental.news.core.MvpView;
import com.blumental.news.db.Article;
import com.blumental.news.model.Model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter implements HomePresenter {

    private HomeView view;
    private Callback refreshCallback = new RefreshCallback();
    private Callback nextPageCallback = new NextPageCallback();
    private int pageNum = 1;
    private String category = "general";
    private boolean canLoadMore = true;

    private Model model;

    @Inject
    MainPresenter(Model model) {
        this.model = model;
    }

    @Override
    public void attach(MvpView mvpView) {
        view = (HomeView) mvpView;
        view.startRefresh();
        model.refresh(category, refreshCallback);
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public boolean canLoadMore() {
        return canLoadMore;
    }

    @Override
    public void onCategoryChange(String category) {
        this.category = category;
        fetchNews();
    }

    @Override
    public void fetchNews() {
        pageNum = 1;
        model.refresh(category, refreshCallback);
    }

    @Override
    public void nextPage() {
        model.getPage(pageNum, category, nextPageCallback);
    }

    private abstract class Callback implements Model.UiCallback {
        @Override
        public void onError(String message) {
            if (view != null) {
                view.stopRefresh();
                view.showError(message);
            }
        }
    }

    private class NextPageCallback extends Callback {
        @Override
        public void onLoaded(List<Article> news, boolean hasMore) {
            canLoadMore = hasMore;
            pageNum++;
            if (view != null) {
                view.stopRefresh();
                view.addNews(convertToVM(news));
            }
        }
    }

    private class RefreshCallback extends Callback {
        @Override
        public void onLoaded(List<Article> news, boolean hasMore) {
            canLoadMore = hasMore;
            pageNum++;
            if (view != null) {
                view.stopRefresh();
                view.setNews(convertToVM(news));
            }
        }
    }

    private static List<ArticleVM> convertToVM(List<Article> news) {
        ArrayList<ArticleVM> models = new ArrayList<>();
        for (Article article : news) {
            models.add(convertToVM(article));
        }
        return models;
    }

    private static ArticleVM convertToVM(Article article) {
        ArticleVM viewModel = new ArticleVM();
        String title = article.title;
        if (title != null) {
            viewModel.title = title.trim();
        }
        String description = article.description;
        if (description != null) {
            viewModel.description = description.trim();
        }
        viewModel.url = article.url.trim();
        viewModel.imageUrl = getImageUrl(article);
        return viewModel;
    }

    private static String getImageUrl(Article article) {
        String url = article.imageUrl;
        if (url == null || url.isEmpty()) {
            return null;
        }
        String trimmed = url.trim();
        if (trimmed.startsWith("//")) {
            return "https:" + trimmed;
        }
        if (trimmed.startsWith("/")) {
            return article.sourceName + trimmed;
        }
        return trimmed;
    }
}
