package edu.umn.d.restaurantpagingapp;

/**
 * Created by tinar on 3/15/2017.
 */

public class Reservation {

    public Reservation(){

    }

    public Reservation(String name, int partySize, int phoneNumber){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
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

    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber(){
        return phoneNumber;
    }

    private String name;
    private int partySize;
    private int phoneNumber;
}
