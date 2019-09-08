package com.blumental.news.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Article.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}