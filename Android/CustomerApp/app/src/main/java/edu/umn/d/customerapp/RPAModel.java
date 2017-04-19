package edu.umn.d.customerapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tinar on 4/4/2017.
 */

public class RPAModel {
    /**
     * Here we will instantiate any of the classes that are used by the model
     * for calculations or otherwise.
     */
    public RPAModel(RPAPresenter presenter){
        this.presenter = presenter;
    }

    /**
     * Create a reservation and add it to the waiting list.
     * @param name  The name of the reservation
     * @param partySize The size of the party
     * @param phoneNumber   The phone number of the customer
     * @param time  The time when the reservation was created
     * @return  A reference to the reservation that was created.
     */
    public Reservation createReservation(String name, int partySize, String phoneNumber, String time,boolean[] accomodations, String otherRequest){

        Reservation res = new Reservation(name,partySize,phoneNumber,time,accomodations,otherRequest);
        this.reservation = res;

        return res;

    }

    public void deleteReservation(){
        reservation = null;
    }



    public Reservation getReservation(){
        return this.reservation;
    }

    RPAPresenter presenter;
    private Reservation reservation;
}
