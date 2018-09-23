package com.bkwong.gistbrowserapp.util;

import com.bkwong.gistbrowserapp.MainThreadBus;
import com.squareup.otto.ThreadEnforcer;

public class BusProvider {

    private static final MainThreadBus BUS = (MainThreadBus) new MainThreadBus(ThreadEnforcer.ANY);

    public static MainThreadBus getInstance() {
        return BUS;
    }

}
