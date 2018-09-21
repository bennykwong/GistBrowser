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

    private static RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private static RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static ArrayList<Gist> publicGists;
    static View.OnClickListener myOnClickListener;
    ApiClient apiClient = null;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;

    private static int page = 2;

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


        apiClient = ApiClient.getApiClient(GistBrowserApplication.getAppContext());

        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                publicGists = response.body();
                adapter = new CustomAdapter(publicGists);
                recyclerView.setAdapter(adapter);

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
                Log.d(TAG, "load more items");
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
                    @Override
                    public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                        publicGists = response.body();
                        adapter = new CustomAdapter(publicGists);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        page = 0;
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                        Log.d(TAG, "print out the the failure reason" + t.getMessage());
                    }
                };
                apiClient.getPublicGists(callBack);
            }
        }, 1000);

        swipeRefreshLayout.setRefreshing(true);
    }
}
