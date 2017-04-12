package edu.umn.d.restaurantpagingapp;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by Jeff Smith on 4/12/2017.
 */

public class ReservationInstanceCreator implements InstanceCreator<Reservation> {
    private String name;
    private int partySize;
    private String phoneNumber;
    private final String time;

    public ReservationInstanceCreator(String name, int partySize, String phoneNumber, String time){
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
        this.time = time;
    }

    @Override
    public Reservation createInstance(Type type) {
        Reservation reservation = new Reservation(name, partySize, phoneNumber,time);
        return reservation;
    }
}
