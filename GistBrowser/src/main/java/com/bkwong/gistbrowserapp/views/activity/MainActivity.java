package com.bkwong.gistbrowserapp.views.activity;

import android.os.Bundle;
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

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Gist> publicGists;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ApiClient apiClient = ApiClient.getApiClient(GistBrowserApplication.getAppContext());

        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                Log.d(TAG, "print out the response" + response.body().toString());
                publicGists = response.body();
                for(Gist gist : publicGists) {
                    Log.d(TAG, "url: " + gist.getUrl());
                    Log.d(TAG, "description: " + gist.getDescription());
                }

                adapter = new CustomAdapter(publicGists);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {

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
}
