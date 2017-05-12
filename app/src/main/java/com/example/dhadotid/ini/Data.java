package com.example.dhadotid.ini;

/**
 * Created by dhadotid on 11/05/2017.
 */
//this code to create object for parsing json

public class Data {
    private String id, judul, isi, userID;

    public Data(){

    }
    public Data(String id, String userID, String judul, String isi){
        this.id = id;
        this.userID = userID;
        this.judul = judul;
        this.isi = isi;
    }
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getUserID(){
        return userID;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getJudul(){
        return judul;
    }
    public void setJudul(String judul){
        this.judul = judul;
    }
    public String getIsi(){
        return isi;
    }
    public void setIsi(String isi){
        this.isi = isi;
    }
}
