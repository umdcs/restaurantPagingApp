package edu.umn.d.customerapp;

import android.view.View;

import java.util.List;

/**
 * Created by tinar on 4/4/2017.
 */

public class RPAPresenter{
    // presenter needs to talk with this model
    private RPAModel mModel;
    private Object mView;

    public RPAPresenter(Object view)
    {
        mView = view;
        mModel = new RPAModel(this);
    }

    /**
     * Edit a reservation in the model.
     * @param index The index of the element in the list
     * @param name  The new name on the reservation
     * @param partySize The new party size on the reservation
     * @param phoneNumber   The new phone number on the reservation
     * @param list  A string determining which list to edit from
     */
    public void editReservation(int index, String name, int partySize, String phoneNumber, String list){

    }

    public void deleteReservation(){
        mModel.deleteReservation();
    }

    public Reservation getReservation(){
        return mModel.getReservation();
    }

    // When the view receives input from the user (after the createReservation button is clicked,
    // this will be called by the view to relay the user data here.  Once here, it can
    // be checked and potentially sent on to the model.
    public void clickCreateReservation(String name, int partySize, String phoneNum, String time) {
        Reservation reservation = mModel.createReservation(name,partySize,phoneNum,time);
    }
}
