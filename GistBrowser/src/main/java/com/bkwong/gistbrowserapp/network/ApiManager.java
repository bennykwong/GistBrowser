package com.bkwong.gistbrowserapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bkwong.gistbrowserapp.Events.GetNextPageGistEvent;
import com.bkwong.gistbrowserapp.Events.GetPublicGistsEvent;
import com.bkwong.gistbrowserapp.Events.UpdateGistsEvent;
import com.bkwong.gistbrowserapp.MainThreadBus;
import com.bkwong.gistbrowserapp.models.Gist;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

//    private static final String TAG = ApiManager.class.getName();
    private static final String TAG = "benny";

    private Context context;
    private MainThreadBus bus;
    private ApiClient apiClient;

    public ApiManager(Context context, MainThreadBus bus) {
        this.context = context;
        this.bus = bus;
        this.apiClient = ApiClient.resetApiClient(this.context);
    }

    @Subscribe
    public void getPublicGistsEvent(final GetPublicGistsEvent event) {
        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                bus.post(new UpdateGistsEvent(response.body(), event.getRequestType()));
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.d(TAG, "print out the the failure reason" + t.getMessage());
            }
        };
        apiClient.getPublicGists(callBack);
    }

    @Subscribe
    public void getNextPageGistsEvent(final GetNextPageGistEvent event) {
        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                bus.post(new UpdateGistsEvent(response.body(), event.getRequestType()));
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.d(TAG, "print out the the failure reason" + t.getMessage());
            }
        };
        apiClient.getPublicGistsPages(event.getPage(), callBack);
    }

}
