package com.blumental.news.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.blumental.news.R;
import com.blumental.news.view.ArticleVM;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

class ArticleHolder extends NewsAdapter.ViewHolder {

    @BindView(R.id.thumbnail)
    ImageView thumbnail;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.description)
    TextView description;

    private PicassoCallback callback;

    ArticleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.callback = new PicassoCallback(thumbnail);
    }

    @Override
    void bindItem(ArticleVM item) {
        setText(title, item.title);
        setText(description, item.description);
        loadImage(item);
        setClickListener(item.url);
    }

    private static void setText(TextView view, String text) {
        if (text != null && !text.isEmpty()) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void loadImage(ArticleVM item) {
        if (item.imageUrl == null) {
            this.thumbnail.setVisibility(View.GONE);
        } else {
            Picasso.get()
                    .load(item.imageUrl)
                    .resize(0, 1200)
                    .onlyScaleDown()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.error)
                    .into(this.thumbnail, callback);
        }
    }

    private void setClickListener(String url) {
        try {
            final Uri uri = Uri.parse(url);
            this.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                view.getContext().startActivity(intent);
            });
        } catch (Exception e) {
            this.itemView.setOnClickListener(null);
        }
    }

    private static class PicassoCallback implements Callback {
        private WeakReference<ImageView> thumbnail;

        PicassoCallback(ImageView thumbnail) {
            this.thumbnail = new WeakReference<>(thumbnail);
        }

        @Override
        public void onSuccess() {
            ImageView view = thumbnail.get();
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onError(Exception e) {
            ImageView view = thumbnail.get();
            if (view != null) {
                view.setVisibility(View.GONE);
            }
            Log.e("ArticleHolder", e.getMessage(), e);
        }
    }
}