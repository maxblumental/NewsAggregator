package com.blumental.news.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface ViewHolderFactory {
    NewsAdapter.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent);
}