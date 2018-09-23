package com.bkwong.gistbrowserapp.controller;

import android.content.Context;

import com.bkwong.gistbrowserapp.MainThreadBus;
import com.bkwong.gistbrowserapp.util.BusProvider;

public abstract class BaseController {
    public Context context;
    public MainThreadBus bus = (MainThreadBus) BusProvider.getInstance();

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
