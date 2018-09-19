package com.bkwong.gistbrowserapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


/**
 * Global container to share info between different components of the application
 */

public class GistBrowserApplication extends MultiDexApplication {
    private static Context mAppContext = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //register for app life cycle callbacks
        registerActivityLifecycleCallbacks(new LifeCycleHandler());

        mAppContext = this;

    }
    public static Context getAppContext() {
        return mAppContext;
    }



}
