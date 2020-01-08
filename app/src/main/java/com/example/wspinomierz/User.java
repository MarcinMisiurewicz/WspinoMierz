package com.example.wspinomierz;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String email;
    public String UID;

    public User(){
        //Default ctor for Firebase
    }

    public User(String email, String UID){
        this.email = email;
        this.UID = UID;
    }
}
