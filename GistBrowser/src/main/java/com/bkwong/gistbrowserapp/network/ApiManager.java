package com.bkwong.gistbrowserapp.network;

import android.content.Context;
import android.util.Log;

import com.bkwong.gistbrowserapp.events.ErrorEvent;
import com.bkwong.gistbrowserapp.events.GetNextPageGistEvent;
import com.bkwong.gistbrowserapp.events.GetPublicGistsEvent;
import com.bkwong.gistbrowserapp.events.UpdateGistsEvent;
import com.bkwong.gistbrowserapp.models.Gist;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {

    private static final String TAG = ApiManager.class.getName();

    private Context context;
    private Bus bus;
    private ApiClient apiClient;

    public ApiManager(Context context, Bus bus) {
        this.context = context;
        this.bus = bus;
        this.apiClient = ApiClient.resetApiClient(this.context);
    }

    @Subscribe
    public void getPublicGistsEvent(final GetPublicGistsEvent event) {
        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                if(response.body() != null) {
                    bus.post(new UpdateGistsEvent(response.body(), event.getRequestType()));
                } else {
                    bus.post(new ErrorEvent(response.message(), response.code()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.d(TAG, "Failed: " + t.getMessage());
            }
        };
        apiClient.getPublicGists(callBack);
    }

    @Subscribe
    public void getNextPageGistsEvent(final GetNextPageGistEvent event) {
        Callback<ArrayList<Gist>> callBack = new Callback<ArrayList<Gist>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Gist>> call, Response<ArrayList<Gist>> response) {
                if(response.body() != null) {
                    bus.post(new UpdateGistsEvent(response.body(), event.getRequestType()));
                } else {
                    bus.post(new ErrorEvent(response.message(), response.code()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Gist>> call, Throwable t) {
                Log.e(TAG, "Failed: " + t.getMessage());
            }
        };
        apiClient.getPublicGistsPages(event.getPage(), callBack);
    }

}
