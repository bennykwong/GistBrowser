package com.bkwong.gistbrowserapp.network;

import com.bkwong.gistbrowserapp.util.Constants;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        Request request = null;

        synchronized (TokenAuthenticator.class) { //perform refresh token in sync blocks, to avoid multiply token updates

            // Add new header to rejected request and retry it
            request = response.request().newBuilder()
                    .removeHeader(Constants.TOKEN_HEADER)
                    .addHeader(Constants.TOKEN_HEADER, Constants.TOKEN_VALUE)
                    .method(response.request().method(), response.request().body())
                    .build();

        }

        if (request != null) {
            return request;
        }

        return null;

    }
}
