package com.muriloorias.bibliotecaApp.model;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("description")
    private String description;
    @SerializedName("is_available")
    private Boolean is_available;

    public Book(String title, String author, String description, Boolean is_available) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.is_available = is_available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }
}
