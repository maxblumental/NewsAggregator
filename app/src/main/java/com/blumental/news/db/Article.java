package com.blumental.news.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Article {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String description;
    public String imageUrl;
    public String url;
    public String sourceName;
}
