package com.muriloorias.bibliotecaApp.model;

import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("text")
    private String text;

    public Comments(String text, Boolean criticize) {
        this.text = text;
        this.criticize = criticize;
    }

    @SerializedName("criticize")
    private Boolean criticize;

    public Boolean getCriticize() {
        return criticize;
    }

    public void setCriticize(Boolean criticize) {
        this.criticize = criticize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
