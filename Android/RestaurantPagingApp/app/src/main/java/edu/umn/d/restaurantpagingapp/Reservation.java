package edu.umn.d.restaurantpagingapp;

import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.telephony.SmsManager;

import java.lang.reflect.Type;

/**
 * Created by tinar on 3/15/2017.
 */

public class Reservation implements java.io.Serializable{

    public Reservation(String name, int partySize, String phoneNumber, String time){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
        this.time = time;

    }

    public String toString(){

        return "Name: " + this.name + "\nParty Size: " + Integer.valueOf(this.partySize) + "\nPhone Number:" + formatPhoneNumber(this.phoneNumber) + "\n" + this.time;
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
    public String formatPhoneNumber(String phoneNum) {
        char[] phoneNumArray = phoneNum.toCharArray();
        return "(" + phoneNumArray[0] + phoneNumArray[1] + phoneNumArray[2] + ")" + phoneNumArray[3] + phoneNumArray[4] + phoneNumArray[5] + "-" + phoneNumArray[6] + phoneNumArray[7] + phoneNumArray[8] + phoneNumArray[9];
    }
    public String getJson() {
        return gson.toJson(this);
    }

    //Helper method creates phone number String
    public String phoneNumber(String phoneNumber){
        char[] phoneNumArray = phoneNumber.toCharArray();
        String phoneNum = "(" + phoneNumArray[0] + phoneNumArray[1] + phoneNumArray[2] + ")" + phoneNumArray[3] + phoneNumArray[4] + phoneNumArray[5] + "-" + phoneNumArray[6] + phoneNumArray[7] + phoneNumArray[8] + phoneNumArray[9];
        return phoneNum;
    }

    private String name;
    private int partySize;
    private String phoneNumber;
    private final String time;
    private final Gson gson = new Gson();
}
