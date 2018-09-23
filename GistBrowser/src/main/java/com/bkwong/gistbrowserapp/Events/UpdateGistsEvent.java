package com.bkwong.gistbrowserapp.Events;

import com.bkwong.gistbrowserapp.models.Gist;

import java.util.ArrayList;

public class UpdateGistsEvent {

    private ArrayList<Gist> gists;

    public UpdateGistsEvent(ArrayList<Gist> gists) {
        this.gists = gists;
    }

    public ArrayList<Gist> getGists() {
        return gists;
    }

}
