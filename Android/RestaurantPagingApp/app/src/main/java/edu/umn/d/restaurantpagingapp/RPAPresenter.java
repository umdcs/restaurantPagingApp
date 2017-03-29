package edu.umn.d.restaurantpagingapp;

import android.util.Log;
import android.widget.EditText;

import java.util.List;

/**
 * Created by melissa on 3/15/17.
 */

public class RPAPresenter implements ModelViewPresenterComponents.RPAPresenterContract {

    // presenter needs to talk with this model
    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;



    public RPAPresenter(ModelViewPresenterComponents.View rpaView)
    {
        mView = rpaView;
        mModel = new RPAModel(this);
    }

    public Reservation getReservation(int index, String list){
        Reservation reservation;
        switch (list){
            case "seated":
                reservation = mModel.getSeatedReservation(index);
                break;
            default:
                reservation = mModel.getReservation(index);
                break;
        }
        return reservation;
    }

    // setting up recycler adapter
    @Override
    public List getReservations(String list)
    {
        List returnedList;
        switch (list){
            case "seated":
                returnedList = mModel.getSeatedReservations();
                break;
            default:
                returnedList = mModel.getReservations();
                break;
        }
        return returnedList;
    }

    public void deleteReservation(int index, String list){
        switch (list){
            case "seated":
                mModel.deleteSeatedReservation(index);
                mView.notifyCustomerListUpdated();
                break;
            case "master":
                mModel.deleteReservation(index);
                mView.notifyCustomerListUpdated();
                break;
        }

    }


    public void moveReservation(int index, String from){
        switch (from){
            case "master":
                mModel.moveToSeated(index);
                mView.notifyCustomerListUpdated();
                break;
            case "seated":
                mModel.moveToMaster(index);
                mView.notifyCustomerListUpdated();
                break;
        }
    }

    public void editReservation(int index, String name, int partySize, String phoneNumber, String list){
        switch (list){
            case "master":
                mModel.editReservation(index,name,partySize,phoneNumber);
                mView.notifyCustomerListUpdated();
                break;

            case "seated":
                mModel.editSeatedReservation(index,name,partySize,phoneNumber);
                mView.notifyCustomerListUpdated();
                break;

        }

    }

    // When the view receives input from the user (after the createReservation button is clicked,
    // this will be called by the view to relay the user data here.  Once here, it can
    // be checked and potentially sent on to the model.
    @Override
    public void clickCreateReservation(String name, int partySize, String phoneNum, String time) {
        Reservation reservation = mModel.createReservation(name,partySize,phoneNum,time);
        mView.addReservationToList(reservation);
    }
}

