package edu.umn.d.restaurantpagingapp;

/**
 * Created by tinar on 3/15/2017.
 */

public class Reservation {

    public Reservation(){

    }

    public Reservation(String name, int partySize, String phoneNumber){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
    }

    public String toString(){
        return this.name + " " + Integer.valueOf(this.partySize) + " " + Integer.valueOf(phoneNumber);
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

    private String name;
    private int partySize;
    private String phoneNumber;
}
