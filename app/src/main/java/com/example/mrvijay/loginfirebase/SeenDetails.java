package com.example.mrvijay.loginfirebase;

public class SeenDetails {

    String personName,time;

    public SeenDetails() {
    }

    public SeenDetails(String personName, String time) {
        this.personName = personName;
        this.time = time;
    }

    public String getPersonName() {
        return personName;
    }

    public String getTime() {
        return time;
    }
}
