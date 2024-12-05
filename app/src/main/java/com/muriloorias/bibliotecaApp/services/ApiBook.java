package com.muriloorias.bibliotecaApp.services;

import com.muriloorias.bibliotecaApp.model.Book;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiBook {

    @GET("books")
    Call<ArrayList<Book>> getBooks();
}
