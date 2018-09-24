package com.bkwong.gistbrowserapp.controller;

import android.content.Context;

import com.bkwong.gistbrowserapp.util.BusProvider;
import com.squareup.otto.Bus;

public abstract class BaseController {
    public Context context;
    public Bus bus = BusProvider.getInstance();

    public BaseController(Context context) {
        this.context = context;
    }

    /**
     * This method is used to receive particular events.
     */
    public void registerForEvents() {
        bus.register(this);
    }

    /**
     * This method is used to unregister class from receiving events.
     */
    public void unregisterForEvents() {
        bus.unregister(this);
    }

}
