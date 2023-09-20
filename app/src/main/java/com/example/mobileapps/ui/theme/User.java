package com.example.mobileapps.ui.theme;

public class User {
    public String userName;

    public String email;

    public static int ID = 1113;

    public User(){
        userName="";
        email="";

    }
    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
        ID++;
    }
    public void setUserName(String newUserName){
        userName = newUserName;
    }

    public int getID(){return ID;}



}
