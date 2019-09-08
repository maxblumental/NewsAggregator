package com.blumental.news.di;

import android.content.Context;
import android.os.Handler;

import androidx.room.Room;

import com.blumental.news.db.ArticleDao;
import com.blumental.news.db.NewsDatabase;
import com.blumental.news.model.Model;
import com.blumental.news.model.ModelImpl;
import com.blumental.news.model.NewsService;
import com.blumental.news.utils.UiThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ModelModule {

    private Handler handler;
    private Context context;

    public ModelModule(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Provides
    @Singleton
    NewsService provideNewsService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);
    }

    @Provides
    @Singleton
    Model provideModel(ModelImpl model) {
        return model;
    }

    @Provides
    @Singleton
    UiThreadExecutor getExecutor() {
        return new UiThreadExecutor(handler);
    }

    @Provides
    @Singleton
    NewsDatabase getDatabase() {
        return Room.databaseBuilder(context, NewsDatabase.class, "database")
                .build();
    }

    @Provides
    @Singleton
    ArticleDao getArticleDao(NewsDatabase database) {
        return database.articleDao();
    }
}
