package com.blumental.news.model;

import com.blumental.news.db.Article;
import com.blumental.news.db.ArticleDao;
import com.blumental.news.model.pojo.ArticleDTO;
import com.blumental.news.model.pojo.Response;
import com.blumental.news.utils.UiThreadExecutor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Call;

public class ModelImpl implements Model {
    private UiThreadExecutor uiThreadExecutor;
    private ExecutorService pool;
    private NewsService service;
    private ArticleDao articleDao;

    @Inject
    ModelImpl(UiThreadExecutor uiThreadExecutor, NewsService service, ArticleDao articleDao) {
        this.service = service;
        this.articleDao = articleDao;
        int nThreads = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(nThreads);
        this.uiThreadExecutor = uiThreadExecutor;
    }

    @Override
    public void getPage(final int pageNumber, String category, final UiCallback callback) {
        callApi(pageNumber, category, callback);
    }

    @Override
    public void refresh(String category, final UiCallback callback) {
        getPage(1, category, callback);
    }

    @SuppressWarnings("DuplicateExpressions")
    private void callApi(final int pageNumber, String category, final UiCallback callback) {
        pool.execute(() -> {
            Call<Response> call = service.getNews(category, pageNumber);
            retrofit2.Response<Response> response;
            try {
                response = call.execute();
            } catch (final IOException e) {
                if (pageNumber == 1) {
                    final List<Article> news = articleDao.getAll();
                    uiThreadExecutor.execute(() -> callback.onLoaded(news, false));
                } else {
                    uiThreadExecutor.execute(() -> callback.onError(e.getMessage()));
                    uiThreadExecutor.execute(() -> callback.onLoaded(Collections.emptyList(), false));
                }
                return;
            }

            Response pojo = response.body();
            if (pojo == null) {
                uiThreadExecutor.execute(() -> callback.onLoaded(Collections.emptyList(), false));
                return;
            }

            final List<Article> news = ArticleDTO.toArticles(pojo.getArticles());
            updateDatabase(pageNumber, news);
            final boolean hasMore = pageNumber * NewsService.PAGE_SIZE < pojo.getTotalResults();
            uiThreadExecutor.execute(() -> callback.onLoaded(news, hasMore));
        });
    }

    private void updateDatabase(int pageNumber, List<Article> news) {
        if (pageNumber == 1) {
            articleDao.deleteAll();
        }
        for (Article article : news) {
            articleDao.insert(article);
        }
    }
}
