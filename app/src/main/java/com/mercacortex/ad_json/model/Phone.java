package com.mercacortex.ad_json.model;

import com.google.gson.annotations.SerializedName;
public class Phone {
    @SerializedName("home")
    private String casa;
    @SerializedName("mobile")
    private String movil;
    @SerializedName("work")
    private String trabajo;
    public String getCasa() {
        return casa;
    }
    public void setCasa(String casa) {
        this.casa = casa;
    }
    public String getMovil() {
        return movil;
    }
    public void setMovil(String movil) {
        this.movil = movil;
    }
    public String getTrabajo() {
        return trabajo;
    }
    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }
}