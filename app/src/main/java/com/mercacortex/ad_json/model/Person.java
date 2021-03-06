package com.mercacortex.ad_json.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Person {
    @SerializedName("contacts")
    public List<Contact> contacts;
    public Person() {
    }
    public List<Contact> getContactos() {
        return contacts;
    }
    public void setContactos(List<Contact> contacts) {
        this.contacts = contacts;
    }
}