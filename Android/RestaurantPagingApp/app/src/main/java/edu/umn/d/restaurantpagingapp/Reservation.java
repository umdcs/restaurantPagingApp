package edu.umn.d.restaurantpagingapp;

import java.util.Comparator;

/**
 * Created by tinar on 3/15/2017.
 */

public class Reservation implements Comparable<Reservation> {

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

    @Override
    public int compareTo(Reservation otherReservation){
        return name.compareTo(otherReservation.name);
    }

    static final Comparator<Reservation> PARTY_SIZE =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return Integer.toString(r1.getPartySize()).compareTo(Integer.toString(r2.getPartySize()));
                }
            };

    static final Comparator<Reservation> TIME_CREATED =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return r1.getTime().compareTo(r2.getTime());
                }
            };

    static final Comparator<Reservation> PHONE_NUMBER =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return r1.getPhoneNumber().compareTo(r2.getPhoneNumber());
                }
            };

    private String name;
    private int partySize;
    private String phoneNumber;
    private final String time;
}
