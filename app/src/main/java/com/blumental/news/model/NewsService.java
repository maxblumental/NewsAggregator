package com.blumental.news.model;

import com.blumental.news.BuildConfig;
import com.blumental.news.model.pojo.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsService {

    int PAGE_SIZE = 20;

    @Headers("X-Api-Key: " + BuildConfig.API_KEY)
    @GET("v2/top-headlines?country=ru&pageSize=" + PAGE_SIZE)
    Call<Response> getNews(@Query("category") String category, @Query("page") int page);
}
