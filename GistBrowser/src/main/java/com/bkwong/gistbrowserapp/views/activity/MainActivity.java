package com.bkwong.gistbrowserapp.views.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bkwong.gistbrowserapp.events.ErrorEvent;
import com.bkwong.gistbrowserapp.events.UpdateGistsEvent;
import com.bkwong.gistbrowserapp.GistBrowserApplication;
import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.controller.ApiController;
import com.bkwong.gistbrowserapp.listeners.CustomScrollListener;
import com.bkwong.gistbrowserapp.models.Gist;
import com.bkwong.gistbrowserapp.util.BusProvider;
import com.bkwong.gistbrowserapp.util.Constants;
import com.bkwong.gistbrowserapp.views.adapter.CustomAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static CustomAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private static RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar appLaunchProgressBar;
    private ProgressBar nextPageProgressBar;
    private static ArrayList<Gist> publicGists;
    private static boolean busRegistered = false;
    private Bus bus = BusProvider.getInstance();

    private ApiController apiController;

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

        appLaunchProgressBar = (ProgressBar) findViewById(R.id.main_progress_bar);
        nextPageProgressBar = (ProgressBar) findViewById(R.id.next_page_progress_bar);

        CustomAdapter.onItemClickListener itemListener = new CustomAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, Gist gist) {
                Intent detailActivity = new Intent(GistBrowserApplication.getAppContext(), DetailScreenActivity.class);
                detailActivity.putExtra("gistData", gist);
                startActivity(detailActivity);
            }
        };

        adapter = new CustomAdapter(this, itemListener);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        apiController = GistBrowserApplication.getApiController();

        apiController.getPublicGist(Constants.DEFAULT);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(busRegistered ==false && bus != null) {
            bus.register(this);
            busRegistered = true;
        }

        if (apiController != null) {
            apiController.registerForEvents();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(busRegistered == true && bus != null) {
            bus.unregister(this);
            busRegistered = false;
        }
        if (apiController != null) {
            apiController.unregisterForEvents();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.addOnScrollListener((new CustomScrollListener(linearLayoutManager) {
            @Override
            protected void loadNextPage() {
                isLoading = true;
                nextPageProgressBar.setVisibility(View.VISIBLE);
                currentPage++;
                apiController.getNextPagePublicGist(Constants.GET_NEXT, currentPage);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRefresh() {
        nextPageProgressBar.setVisibility(View.GONE);
        apiController.getPublicGist(Constants.REFRESH);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Subscribe
    public void updateGistsEvent(UpdateGistsEvent event) {
        switch (event.getRequestType()) {
            case Constants.DEFAULT:
            default:
                publicGists = event.getGists();
                appLaunchProgressBar.setVisibility(View.GONE);
                adapter.addAllGist(publicGists);
                break;
            case Constants.REFRESH:
                adapter.clear();
                publicGists = event.getGists();
                adapter.addAllGist(publicGists);
                swipeRefreshLayout.setRefreshing(false);
                currentPage = PAGE_START;
                break;
            case Constants.GET_NEXT:
                publicGists = event.getGists();
                nextPageProgressBar.setVisibility(View.GONE);
                adapter.addAllGist(publicGists);
                isLoading = false;
                break;
        }
    }

    @Subscribe
    public void errorEvent(ErrorEvent event) {
        nextPageProgressBar.setVisibility(View.GONE);
        appLaunchProgressBar.setVisibility(View.GONE);
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        switch (event.getErrorCode()) {
            case Constants.ERROR_401:
                alertDialog.setTitle(getString(R.string.error_401_title));
                alertDialog.setMessage(getString(R.string.error_401_message));
                break;
            case Constants.ERROR_403:
                alertDialog.setTitle(getString(R.string.error_403_title));
                alertDialog.setMessage(getString(R.string.error_403_message));
                break;
            default:
                alertDialog.setTitle(getString(R.string.error_dialog_title));
                alertDialog.setMessage(getString(R.string.error_dialog_message));
                break;
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }

}
