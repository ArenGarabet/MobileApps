package com.example.mobileapps.ui.theme;

public class User {
    public String userName;

    public String email;

    public static int ID = 1112;

    public User(){
        userName="";
        email="";

    }
    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
        ID++;
    }

    public int getID(){return ID;}



}
