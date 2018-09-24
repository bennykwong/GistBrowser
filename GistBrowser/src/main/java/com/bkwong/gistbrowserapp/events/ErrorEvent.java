package com.bkwong.gistbrowserapp.events;

public class ErrorEvent {
    private String errorReason;
    private int errorCode;

    public ErrorEvent(String errorReason, int errorCode) {
        this.errorReason = errorReason;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorReason() {
        return errorReason;
    }

}
