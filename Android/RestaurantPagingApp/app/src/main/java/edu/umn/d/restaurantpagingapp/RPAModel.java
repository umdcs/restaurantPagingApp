package edu.umn.d.restaurantpagingapp;

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

    }

    public void moveToSeated(int index){
        Reservation res = (Reservation)waitingReservations.remove(index);
        seatedReservations.add(res);
    }

    public void createReservation(String name, int partySize, int phoneNumber){
        Reservation res = new Reservation(name,partySize,phoneNumber);
        waitingReservations.add(res);

        Log.d("Resrevation","Created reservation");

    }

    ModelViewPresenterComponents.RPAPresenterContract presenter;
    List waitingReservations = new ArrayList();
    List seatedReservations = new ArrayList();
}
