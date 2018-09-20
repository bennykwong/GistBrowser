package com.bkwong.gistbrowserapp.network;

import com.bkwong.gistbrowserapp.models.Gist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubAPI {

    @GET("/gists/public")
    Call<ArrayList<Gist>> getPublicGists();
}
