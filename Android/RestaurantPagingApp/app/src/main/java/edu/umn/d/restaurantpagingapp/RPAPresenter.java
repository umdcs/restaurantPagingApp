package edu.umn.d.restaurantpagingapp;

import java.util.List;

/**
 * Created by melissa on 3/15/17.
 */

public class RPAPresenter implements ModelViewPresenterComponents.RPAPresenterContract {

    // presenter needs to talk with this model
    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;


    /**
     * Constructor
     * @param rpaView The view that this presenter is presenting for
     */
    public RPAPresenter(ModelViewPresenterComponents.View rpaView)
    {
        mView = rpaView;
        mModel = new RPAModel();
    }

    /**
     * Get a single reservation from a list.
     * @param index Index indicating which element to get
     * @param list  Which list to get from
     * @return  A reservation at the specified index
     */
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

    /**
     * Get all the reservations of one of the lists
     * @param list  Which list to get reservations from
     * @return  A list containing all the reservations in one of the lists
     */
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

    /**
     * Deletes a reservation from a list
     * @param index Index of the reservation to delete
     * @param list  Which list to delete from
     */
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

    /**
     * Move a reservation from one list to another
     * @param index Index of the reservation to move.
     * @param from  Which list to move from
     */
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

    /**
     * Edit a reservation in the model.
     * @param index The index of the element in the list
     * @param name  The new name on the reservation
     * @param partySize The new party size on the reservation
     * @param phoneNumber   The new phone number on the reservation
     * @param list  A string determining which list to edit from
     */
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

