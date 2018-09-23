package com.bkwong.gistbrowserapp.Events;

import com.bkwong.gistbrowserapp.models.Gist;

import java.util.ArrayList;

public class UpdateGistsEvent {

    private ArrayList<Gist> gists;

    private boolean isRefresh;

    public UpdateGistsEvent(ArrayList<Gist> gists, boolean isRefresh) {
        this.gists = gists;
        this.isRefresh = isRefresh;
    }

    public ArrayList<Gist> getGists() {
        return gists;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

}
