package com.bkwong.gistbrowserapp.network;

import com.bkwong.gistbrowserapp.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = null;
        if (chain != null) {
            request = chain.request();
            request = addTokenToRequest(request);
        }
        Response response = null;
        if (request != null) {
            response = chain.proceed(request);
        }
        return response;
    }

    private Request addTokenToRequest(Request originalRequest) {
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .addHeader(Constants.TOKEN_HEADER, Constants.TOKEN_TYPE + " " + Constants.TOKEN_VALUE)
                .method(originalRequest.method(), originalRequest.body());

        return requestBuilder.build();
    }
}
