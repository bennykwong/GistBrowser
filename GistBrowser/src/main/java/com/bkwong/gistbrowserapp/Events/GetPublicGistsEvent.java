package com.bkwong.gistbrowserapp.Events;

public class GetPublicGistsEvent {
    private boolean isRefresh = false;

    public GetPublicGistsEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

}
