package com.bkwong.gistbrowserapp;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class MainThreadBus extends Bus{
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public MainThreadBus(ThreadEnforcer threadEnforcer) {
        super(threadEnforcer);
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}
