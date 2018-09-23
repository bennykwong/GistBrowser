package com.bkwong.gistbrowserapp.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.bkwong.gistbrowserapp.Events.GetPublicGistsEvent;

public class ApiController extends BaseController{

    private static final String TAG = ApiController.class.getSimpleName();

    private Handler handler = new Handler(Looper.getMainLooper());

    public ApiController(Context context) {
        super(context);
    }

    public void getPublicGist(boolean isRefresh) {
        bus.post(new GetPublicGistsEvent(isRefresh));
    }

}
