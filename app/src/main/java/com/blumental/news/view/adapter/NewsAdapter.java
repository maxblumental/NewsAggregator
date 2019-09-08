package com.blumental.news.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.blumental.news.view.ArticleVM;
import com.blumental.news.view.HomePresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private HomePresenter presenter;

    private List<ArticleVM> news;


    public NewsAdapter(HomePresenter presenter) {
        this.presenter = presenter;
    }

    public void setNews(List<ArticleVM> news) {
        this.news = new ArrayList<>(news);
        notifyDataSetChanged();
    }

    public void addNews(List<ArticleVM> news) {
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == news.size() && presenter.canLoadMore()) {
            return NewsItemType.LOADING.ordinal();
        }
        return NewsItemType.NEWS.ordinal();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewsItemType postType = NewsItemType.values()[viewType];
        return postType.createViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        if (getItemViewType(position) == NewsItemType.LOADING.ordinal()) {
            presenter.nextPage();
        }
        ArticleVM item = news.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }

    abstract static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        abstract void bindItem(ArticleVM item);
    }
}