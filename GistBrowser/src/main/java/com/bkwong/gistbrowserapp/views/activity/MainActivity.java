package com.bkwong.gistbrowserapp.views.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bkwong.gistbrowserapp.GistBrowserApplication;
import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.listeners.CustomScrollListener;
import com.bkwong.gistbrowserapp.models.Gist;
import com.bkwong.gistbrowserapp.network.ApiClient;
import com.bkwong.gistbrowserapp.views.adapter.CustomAdapter;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

//    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = "benny";

    private static CustomAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private static RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static ArrayList<Gist> publicGists;
    static View.OnClickListener myOnClickListener;
    ApiClient apiClient = null;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private int currentPage = PAGE_START;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new CustomAdapter(this);
        recyclerView.setAdapter(adapter);

        apiClient = ApiClient.getApiClient(GistBrowserApplication.getAppContext());

        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                Log.d(TAG, "response header: " + response.headers().toString());
                publicGists = response.body();
                adapter.addAllGist(publicGists);
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.d(TAG, "print out the the failure reason" + t.getMessage());
            }
        };
        apiClient.getPublicGists(callBack);



        recyclerView.addOnScrollListener((new CustomScrollListener(linearLayoutManager) {
            @Override
            protected void loadNextPage() {
                isLoading = true;
                currentPage++;

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadNext();
                    }
                });
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
                    @Override
                    public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                        publicGists = response.body();
                        adapter.clear();
                        adapter.addAllGist(publicGists);
                        swipeRefreshLayout.setRefreshing(false);
                        currentPage = PAGE_START;
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                        Log.d(TAG, "print out the the failure reason" + t.getMessage());
                    }
                };
                apiClient.getPublicGists(callBack);
            }
        });

        swipeRefreshLayout.setRefreshing(true);
    }

    private void loadNext() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                publicGists = response.body();
                adapter.addAllGist(publicGists);
                isLoading = false;
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.d(TAG, "print out the the failure reason" + t.getMessage());
            }
        };
        apiClient.getPublicGistsPages(currentPage, callBack);

    }
}
