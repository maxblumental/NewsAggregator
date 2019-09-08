package com.blumental.news.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Insert
    void insert(Article article);

    @Query("DELETE FROM article")
    void deleteAll();
}
