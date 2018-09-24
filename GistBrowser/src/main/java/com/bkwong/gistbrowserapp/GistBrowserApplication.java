package com.bkwong.gistbrowserapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.bkwong.gistbrowserapp.controller.ApiController;
import com.bkwong.gistbrowserapp.network.ApiManager;
import com.bkwong.gistbrowserapp.util.BusProvider;
import com.squareup.otto.Bus;

/**
 * Global container to share info between different components of the application
 */

public class GistBrowserApplication extends MultiDexApplication {
    private static Context mAppContext = null;

    private static ApiManager apiManager;
    private static Bus bus = BusProvider.getInstance();
    private static ApiController mApiController;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiController = new ApiController(this);
        setApiManager();
        mAppContext = this;

    }
    public static Context getAppContext() {
        return mAppContext;
    }

    public static ApiController getApiController()
    {
        return mApiController;
    }

    public synchronized static void setApiManager() {
        if (apiManager != null) {
            bus.unregister(apiManager);
            apiManager = null;
        }
        apiManager = new ApiManager(getAppContext(), bus);
        bus.register(apiManager);
    }

}
