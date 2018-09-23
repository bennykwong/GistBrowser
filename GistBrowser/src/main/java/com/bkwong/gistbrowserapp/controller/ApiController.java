package com.bkwong.gistbrowserapp.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.bkwong.gistbrowserapp.Events.GetNextPageGistEvent;
import com.bkwong.gistbrowserapp.Events.GetPublicGistsEvent;

public class ApiController extends BaseController{

    private static final String TAG = ApiController.class.getSimpleName();

    private Handler handler = new Handler(Looper.getMainLooper());

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
