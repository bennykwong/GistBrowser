package com.bkwong.gistbrowserapp.Events;

public class GetPublicGistsEvent {
    private String requestType;

    public GetPublicGistsEvent(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

}
