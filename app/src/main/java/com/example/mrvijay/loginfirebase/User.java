package com.example.mrvijay.loginfirebase;

/**
 * Created by Mr.vijay on 28-10-2018.
 */

public class User {

   public String name;
   public  String email;
   public  String fcma_id;

    public User(String name, String email, String fcma_id) {
        this.name = name;
        this.email = email;
        this.fcma_id = fcma_id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcma_id() {
        return fcma_id;
    }

    public void setFcma_id(String fcma_id) {
        this.fcma_id = fcma_id;
    }
}
