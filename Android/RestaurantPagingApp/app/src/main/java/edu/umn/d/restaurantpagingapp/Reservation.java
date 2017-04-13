package edu.umn.d.restaurantpagingapp;


import com.google.gson.Gson;
import java.util.Comparator;


import java.lang.reflect.Type;

/**
 * Created by tina on 3/15/2017.
 */

public class Reservation implements Comparable<Reservation> {

    public Reservation(String name, int partySize, String phoneNumber, String time){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
        this.time = time;

    }

    public String toString(){

        return "Name: " + this.name + "\nParty Size: " + this.partySize + "\nPhone Number: " + formatPhoneNumber(this.phoneNumber) + "\n" + this.time;

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


    //Helper method creates phone number String
    private String formatPhoneNumber(String phoneNum) {
        char[] phoneNumArray = phoneNum.toCharArray();
        return "(" + phoneNumArray[0] + phoneNumArray[1] + phoneNumArray[2] + ")" + phoneNumArray[3] + phoneNumArray[4] + phoneNumArray[5] + "-" + phoneNumArray[6] + phoneNumArray[7] + phoneNumArray[8] + phoneNumArray[9];
    }

    public String getJson() {
        return gson.toJson(this);
    }

    @Override
    public int compareTo(Reservation otherReservation){
        return name.compareTo(otherReservation.name);
    }

    static final Comparator<Reservation> PARTY_SIZE_ASC =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return Integer.toString(r1.getPartySize()).compareTo(Integer.toString(r2.getPartySize()));
                }
            };

    static final Comparator<Reservation> PARTY_SIZE_DESC =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return Integer.toString(r2.getPartySize()).compareTo(Integer.toString(r1.getPartySize()));
                }
            };

    static final Comparator<Reservation> TIME_CREATED_ASC =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return r1.getTime().compareTo(r2.getTime());
                }
            };

    static final Comparator<Reservation> TIME_CREATED_DESC =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return r2.getTime().compareTo(r1.getTime());
                }
            };

    static final Comparator<Reservation> PHONE_NUMBER_ASC =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return r1.getPhoneNumber().compareTo(r2.getPhoneNumber());
                }
            };

    static final Comparator<Reservation> PHONE_NUMBER_DESC =
            new Comparator<Reservation>() {
                public int compare(Reservation r1, Reservation r2) {
                    return r2.getPhoneNumber().compareTo(r1.getPhoneNumber());
                }
            };
    
    private String name;
    private int partySize;
    private String phoneNumber;
    private final String time;
    private final Gson gson = new Gson();
}
