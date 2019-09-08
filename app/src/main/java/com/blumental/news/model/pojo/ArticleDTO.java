package com.blumental.news.model.pojo;

import com.google.gson.annotations.SerializedName;
import com.blumental.news.db.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDTO {
    private String title;
    private String description;
    @SerializedName("urlToImage")
    private String imageUrl;
    private Source source;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Article toArticle() {
        Article article = new Article();
        article.title = title;
        article.description = description;
        article.url = url;
        article.imageUrl = imageUrl;
        if (source != null) {
            article.sourceName = source.getName();
        }
        return article;
    }

    public static List<Article> toArticles(List<ArticleDTO> dtos) {
        ArrayList<Article> result = new ArrayList<>();
        for (ArticleDTO dto : dtos) {
            result.add(dto.toArticle());
        }
        return result;
    }
}
