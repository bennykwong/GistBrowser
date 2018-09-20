package com.bkwong.gistbrowserapp.views.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.bkwong.gistbrowserapp.GistBrowserApplication;
import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.models.Gist;
import com.bkwong.gistbrowserapp.models.Gists;
import com.bkwong.gistbrowserapp.network.ApiClient;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ApiClient apiClient = ApiClient.getApiClient(GistBrowserApplication.getAppContext());

        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                Log.d(TAG, "print out the response" + response.body().toString());
                ArrayList<Gist> publicGists = response.body();
                for(Gist gist : publicGists) {
                    Log.d(TAG, "url: " + gist.getUrl());
                    Log.d(TAG, "description: " + gist.getDescription());
                }

            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {

            }
        };
        apiClient.getPublicGists(callBack);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
