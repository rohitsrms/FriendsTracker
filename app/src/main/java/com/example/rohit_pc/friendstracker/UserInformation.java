package com.example.rohit_pc.friendstracker;


public class UserInformation {
    String name;
    String address;
    double lat;
    double lang;
    String uid;
    String email;


    public UserInformation(String name, String address, double lat, double lang, String uid,String email) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lang = lang;
        this.uid=uid;
        this.email=email;

    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String adress) {
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLang() {
        return lang;
    }



}
