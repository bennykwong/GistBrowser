package com.bkwong.gistbrowserapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class LifeCycleHandler implements Application.ActivityLifecycleCallbacks{
    private int numOfActivities = 0;
    private boolean isStarted = false;
    private static final String TAG = LifeCycleHandler.class.getSimpleName();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!isStarted) {
            isStarted = true;
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (numOfActivities == 0) {
            //app terminated
            isStarted = false;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (numOfActivities == 0) {
            //app is foreground
        }
        numOfActivities++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        numOfActivities--;
        if (numOfActivities == 0) {
            //app is background

        }
    }

}
