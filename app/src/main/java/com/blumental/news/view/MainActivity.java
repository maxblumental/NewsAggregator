package com.blumental.news.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blumental.news.NewsAggregatorApp;
import com.blumental.news.R;
import com.blumental.news.view.adapter.NewsAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeView {

    private Map<Integer, String> categoryMap = new HashMap<Integer, String>() {{
        put(R.id.general, "general");
        put(R.id.business, "business");
        put(R.id.entertainment, "entertainment");
        put(R.id.health, "health");
        put(R.id.sports, "sports");
        put(R.id.technology, "technology");
        put(R.id.science, "science");
    }};

    @Inject
    protected HomePresenter presenter;

    private NewsAdapter adapter;

    @BindView(R.id.newsList)
    protected RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    protected SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NewsAggregatorApp.component.inject(this);
        adapter = new NewsAdapter(presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(() -> presenter.fetchNews());
        presenter.attach(this);
    }


    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.general:
            case R.id.business:
            case R.id.entertainment:
            case R.id.health:
            case R.id.science:
            case R.id.sports:
            case R.id.technology:
                String category = categoryMap.get(item.getItemId());
                presenter.onCategoryChange(category);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setNews(List<ArticleVM> items) {
        adapter.setNews(items);
    }

    @Override
    public void addNews(List<ArticleVM> items) {
        adapter.addNews(items);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startRefresh() {
        refreshLayout.setRefreshing(true);
    }
}
