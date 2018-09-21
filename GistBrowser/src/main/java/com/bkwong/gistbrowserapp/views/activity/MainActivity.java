package com.bkwong.gistbrowserapp.views.activity;

import android.os.Bundle;
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
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static ArrayList<Gist> publicGists;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ApiClient apiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


        apiClient = ApiClient.getApiClient(GistBrowserApplication.getAppContext());

        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                Log.d(TAG, "print out the response" + response.body().toString());
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



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                Log.d(TAG, "print out the response" + response.body().toString());
                publicGists = response.body();
                adapter = new CustomAdapter(publicGists);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.d(TAG, "print out the the failure reason" + t.getMessage());
            }
        };
        apiClient.getPublicGists(callBack);
        swipeRefreshLayout.setRefreshing(true);
    }
}
