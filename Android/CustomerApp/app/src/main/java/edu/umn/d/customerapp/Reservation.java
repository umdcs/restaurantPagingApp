package edu.umn.d.customerapp;

import com.google.gson.Gson;

/**
 * Created by tinar on 4/4/2017.
 */

public class Reservation {

    public Reservation(String name, int partySize, String phoneNumber, String time, boolean[] requests, String otherRequestString){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
        this.time = time;

        this.highChair = requests[0];
        this.booth = requests[1];
        this.wheelChair = requests[2];
        this.willSplit = requests[3];
        this.otherRequest = requests[4];

        this.otherRequestString = otherRequestString;

        this.isSeated = false;

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

    //Special Requests
    public boolean highChairRequested(){
        return highChair;
    }

    public boolean boothRequested(){
        return booth;
    }

    public boolean wheelChairRequested() {
        return wheelChair;
    }

    public boolean willSplitRequested(){
        return willSplit;
    }

    public boolean otherRequested(){
        return otherRequest;
    }

    public String getOtherRequest() {
        return otherRequestString;
    }


    public boolean isSeated() {
        return this.isSeated;
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
    private boolean highChair;
    private boolean booth;
    private boolean wheelChair;
    private boolean willSplit;
    private boolean otherRequest;
    private String otherRequestString;
    private boolean isSeated;
}
