package edu.umn.d.restaurantpagingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff Smith on 3/15/2017.
 */

public class RPAModel implements ModelViewPresenterComponents.Model {
    /**
     * Here we will instantiate any of the classes that are used by the model
     * for calculations or otherwise.
     */
    public RPAModel(ModelViewPresenterComponents.RPAPresenterContract presenter){
        this.presenter = presenter;
    }

    public List getAllReservations(){
        return waitingReservations;
    }


    public List getSeatedReservations() {return seatedReservations;}

    public void addReservation(Reservation reservation){
        waitingReservations.add(reservation);
    }


    public void createReservation(String name, int partySize, String phoneNumber, String time){
        
        Reservation res = new Reservation(name,partySize,phoneNumber,time);
        waitingReservations.add(res);

        Log.d("Resrevation","Created reservation");

    }

    public void editReservation(int index, String name, int partySize, String phoneNumber){
        Reservation res = (Reservation)waitingReservations.remove(index);
        res.setName(name);
        res.setPartySize(partySize);
        res.setPhoneNumber(phoneNumber);
        waitingReservations.add(index, res);
    }

    public void deleteReservation(int index){
        waitingReservations.remove(index);
    }

    public void moveToSeated(int position){
        Object res = waitingReservations.remove(position);
        seatedReservations.add(res);
        Reservation reservation = (Reservation) res;
        Log.d("Obj", reservation.toString());
        Log.d("Res", seatedReservations.toString());

    }

    ModelViewPresenterComponents.RPAPresenterContract presenter;
    List waitingReservations = new ArrayList();
    List seatedReservations = new ArrayList();
}
