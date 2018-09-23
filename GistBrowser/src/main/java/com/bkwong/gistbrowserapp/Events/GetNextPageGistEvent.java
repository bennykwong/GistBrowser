package com.bkwong.gistbrowserapp.Events;

public class GetNextPageGistEvent {
    private String requestType;
    private int page;

    public GetNextPageGistEvent(String requestType, int page) {
        this.requestType = requestType;
        this.page = page;
    }

    public String getRequestType() {
        return requestType;
    }

    public int getPage() {
        return page;
    }

}
