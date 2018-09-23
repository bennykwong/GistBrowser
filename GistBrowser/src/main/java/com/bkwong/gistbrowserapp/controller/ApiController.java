package com.bkwong.gistbrowserapp.controller;

import android.content.Context;

import com.bkwong.gistbrowserapp.events.GetNextPageGistEvent;
import com.bkwong.gistbrowserapp.events.GetPublicGistsEvent;

public class ApiController extends BaseController{

    private static final String TAG = ApiController.class.getSimpleName();

    public ApiController(Context context) {
        super(context);
    }

    public void getPublicGist(String requestType) {
        bus.post(new GetPublicGistsEvent(requestType));
    }

    public void getNextPagePublicGist(String requestType, int page) {
        bus.post(new GetNextPageGistEvent(requestType, page));
    }

}
