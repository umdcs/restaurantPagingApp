package edu.umn.d.restaurantpagingapp;

import com.google.gson.Gson;

/**
 * Created by tinar on 3/15/2017.
 */

public class Reservation {

    public Reservation(String name, int partySize, String phoneNumber, String time){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
        this.time = time;
    }

    public String toString(){

        return "Name: " + this.name + "\nParty Size: " + Integer.valueOf(this.partySize) + "\nPhone Number:" + this.phoneNumber + "\n" + this.time;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPartySize(int partySize){
        this.partySize = partySize;
    }

    public int getPartySize(){
        return partySize;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public String getJson() {
        return gson.toJson(this);
    }

    private String name;
    private int partySize;
    private String phoneNumber;
    private final String time;
    private final Gson gson = new Gson();
}
