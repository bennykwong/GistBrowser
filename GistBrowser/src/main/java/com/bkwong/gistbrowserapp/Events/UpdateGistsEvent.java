package com.bkwong.gistbrowserapp.Events;

import com.bkwong.gistbrowserapp.models.Gist;

import java.util.ArrayList;

public class UpdateGistsEvent {

    private ArrayList<Gist> gists;

    private String requestType;

    public UpdateGistsEvent(ArrayList<Gist> gists, String requestType) {
        this.gists = gists;
        this.requestType = requestType;
    }

    public ArrayList<Gist> getGists() {
        return gists;
    }

    public String getRequestType() {
        return requestType;
    }

}
