package com.bkwong.gistbrowserapp.Events;

public class GetNextPageGistEvent {
    private String requestType;

    public GetNextPageGistEvent(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

}
