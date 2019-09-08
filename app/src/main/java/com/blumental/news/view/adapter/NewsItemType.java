package com.blumental.news.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blumental.news.R;

public enum NewsItemType implements ViewHolderFactory {
    NEWS {
        @Override
        public NewsAdapter.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent) {
            View view = inflater.inflate(R.layout.article_holder, parent, false);
            return new ArticleHolder(view);
        }
    },

    LOADING {
        @Override
        public NewsAdapter.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent) {
            View view = inflater.inflate(R.layout.loading, parent, false);
            return new LoadingHolder(view);
        }
    }
}