package com.muriloorias.bibliotecaApp.services;

import com.muriloorias.bibliotecaApp.model.Comments;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiComments {
    String urlStandard = "comments";

    @GET(urlStandard)
    Call<ArrayList<Comments>> getComments();

    @POST(urlStandard)
    Call<Void> postComment(@Body Comments comments);
}
