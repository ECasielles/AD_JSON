package com.mercacortex.ad_json.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Titular {private String web;
    private String feed;
    @SerializedName("titles")
    private List<Noticia> titulares;

    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }
    public String getFeed() {
        return feed;
    }
    public void setFeed(String feed) {
        this.feed = feed;
    }
    public List<Noticia> getTitulares() {
        return titulares;
    }
    public void setTitulares(List<Noticia> titulares) {
        this.titulares = titulares;
    }
}
