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
        this.createReservationPresenter = new CreateReservationPresenter(this);
    }

    public List getAllReservations(){
        return waitingReservations;
    }

    public void addReservation(Reservation reservation){
        waitingReservations.add(reservation);
    }

    public void createReservation(String name, int partySize, String phoneNumber){
        Reservation res = new Reservation(name,partySize,phoneNumber);
        waitingReservations.add(res);

        Log.d("Resrevation","Created reservation");

    }

    ModelViewPresenterComponents.RPAPresenterContract presenter;
    private CreateReservationPresenter createReservationPresenter;
    List waitingReservations = new ArrayList();
    List seatedReservations = new ArrayList();
}
